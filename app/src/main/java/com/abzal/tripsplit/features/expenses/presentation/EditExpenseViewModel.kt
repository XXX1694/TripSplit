package com.abzal.tripsplit.features.expenses.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.features.expenses.domain.model.Expense
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

data class EditExpenseUiState(
    val trip: Trip? = null,
    val draft: ExpenseDraft = ExpenseDraft(),
    val participants: List<Participant> = emptyList(),
    val isLoaded: Boolean = false,
    val isSaving: Boolean = false,
)

class EditExpenseViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val expenseRepository: ExpenseRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])
    private val expenseId: String = checkNotNull(savedStateHandle["expenseId"])
    private var expense: Expense? = null

    private val _uiState = MutableStateFlow(EditExpenseUiState())
    val uiState: StateFlow<EditExpenseUiState> = _uiState.asStateFlow()

    init {
        // The form is filled once; later repository updates must not overwrite what the user types.
        viewModelScope.launch {
            val loaded = expenseRepository.observeExpense(expenseId).filterNotNull().first()
            expense = loaded
            _uiState.update {
                it.copy(
                    trip = tripRepository.observeTrip(tripId).first(),
                    participants = participantRepository.observeParticipants(tripId).first(),
                    draft = loaded.toDraft(),
                    isLoaded = true,
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
            expenseRepository.updateExpense(draft.toExpense(tripId, base = expense))
            _uiState.update { it.copy(isSaving = false) }
            onSaved()
        }
    }

    fun delete(onDeleted: () -> Unit) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(expenseId)
            onDeleted()
        }
    }
}
