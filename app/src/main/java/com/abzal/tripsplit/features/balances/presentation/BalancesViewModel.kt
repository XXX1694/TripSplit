package com.abzal.tripsplit.features.balances.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.balances.domain.model.Balance
import com.abzal.tripsplit.features.balances.domain.model.Settlement
import com.abzal.tripsplit.features.balances.domain.model.Transfer
import com.abzal.tripsplit.features.balances.domain.repository.BalanceRepository
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

data class BalancesUiState(
    val trip: Trip? = null,
    val balances: List<Balance> = emptyList(),
    val settlements: List<Settlement> = emptyList(),
    val transfers: List<Transfer> = emptyList(),
    val expenses: List<Expense> = emptyList(),
    val participants: List<Participant> = emptyList(),
) {
    val currency: String get() = trip?.currency.orEmpty()

    val totalSpent: Double get() = expenses.sumOf { it.amount }

    val perPerson: Double get() = if (participants.isEmpty()) 0.0 else totalSpent / participants.size

    val settledTotal: Double get() = settlements.sumOf { it.amount }

    /** Money that is still owed to those who paid more than their share. */
    val unsettledTotal: Double get() = balances.filter { it.amount > 0 }.sumOf { it.amount }

    fun paidBy(participantId: String): Double =
        expenses.filter { it.paidById == participantId }.sumOf { it.amount }

    fun shareOf(participantId: String): Double =
        expenses.filter { participantId in it.participantIds }.sumOf { it.amount / it.participantIds.size }
}

class BalancesViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val expenseRepository: ExpenseRepository,
    private val balanceRepository: BalanceRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(BalancesUiState())
    val uiState: StateFlow<BalancesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tripRepository.observeTrip(tripId).collect { value -> _uiState.update { it.copy(trip = value) } }
        }
        viewModelScope.launch {
            expenseRepository.observeExpenses(tripId).collect { value -> _uiState.update { it.copy(expenses = value) } }
        }
        viewModelScope.launch {
            balanceRepository.observeBalances(tripId).collect { value -> _uiState.update { it.copy(balances = value) } }
        }
        viewModelScope.launch {
            balanceRepository.observeSettlements(tripId).collect { value -> _uiState.update { it.copy(settlements = value) } }
        }
        viewModelScope.launch {
            balanceRepository.observeOptimizedTransfers(tripId).collect { value -> _uiState.update { it.copy(transfers = value) } }
        }
        viewModelScope.launch {
            participantRepository.observeParticipants(tripId).collect { value -> _uiState.update { it.copy(participants = value) } }
        }
    }
}
