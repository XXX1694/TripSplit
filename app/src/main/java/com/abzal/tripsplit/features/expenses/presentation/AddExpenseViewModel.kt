package com.abzal.tripsplit.features.expenses.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.features.expenses.domain.repository.ExpenseRepository
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddExpenseUiState(
    val trip: Trip? = null,
    val draft: ExpenseDraft = ExpenseDraft(),
    val participants: List<Participant> = emptyList(),
    val isSaving: Boolean = false,
)

class AddExpenseViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val expenseRepository: ExpenseRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState: StateFlow<AddExpenseUiState> = _uiState.asStateFlow()

    init {
        // Start with the trip currency, the first participant as payer and everyone in the split.
        viewModelScope.launch {
            val trip = tripRepository.observeTrip(tripId).filterNotNull().first()
            val participants = participantRepository.observeParticipants(tripId).first()
            _uiState.update {
                it.copy(
                    trip = trip,
                    participants = participants,
                    draft = it.draft.copy(
                        currency = trip.currency,
                        paidById = participants.firstOrNull()?.id,
                        participantIds = participants.map { p -> p.id }.toSet(),
                    ),
                )
            }
        }
    }

    fun onDraftChange(draft: ExpenseDraft) {
        _uiState.update { it.copy(draft = draft) }
    }

    fun onCurrencyPicked(currency: String) {
        _uiState.update { it.copy(draft = it.draft.copy(currency = currency)) }
    }

    fun save(onSaved: () -> Unit) {
        val draft = _uiState.value.draft
        if (!draft.isValid) return
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            expenseRepository.addExpense(draft.toExpense(tripId))
            _uiState.update { it.copy(isSaving = false) }
            onSaved()
        }
    }
}
