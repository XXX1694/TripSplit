package com.abzal.tripsplit.features.expenses.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.core.navigation.expenseId
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.expenses.domain.repository.ExpenseRepository
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ExpenseDetailUiState(
    val expense: Expense? = null,
    val trip: Trip? = null,
    val participants: List<Participant> = emptyList(),
) {
    /** Participants who split the expense (equal split). */
    val sharingParticipants: List<Participant>
        get() = expense?.participantIds.orEmpty().mapNotNull { id -> participants.firstOrNull { it.id == id } }

    val sharePerPerson: Double
        get() = expense?.let { it.amount / it.participantIds.size.coerceAtLeast(1) } ?: 0.0

    val payer: Participant? get() = participants.firstOrNull { it.id == expense?.paidById }
}

class ExpenseDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val expenseRepository: ExpenseRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])
    private val expenseId: String = checkNotNull(savedStateHandle["expenseId"])

    private val _uiState = MutableStateFlow(ExpenseDetailUiState())
    val uiState: StateFlow<ExpenseDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            expenseRepository.observeExpense(expenseId).collect { value -> _uiState.update { it.copy(expense = value) } }
        }
        viewModelScope.launch {
            tripRepository.observeTrip(tripId).collect { value -> _uiState.update { it.copy(trip = value) } }
        }
        viewModelScope.launch {
            participantRepository.observeParticipants(tripId).collect { value -> _uiState.update { it.copy(participants = value) } }
        }
    }

    fun delete(onDeleted: () -> Unit) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(expenseId)
            onDeleted()
        }
    }
}
