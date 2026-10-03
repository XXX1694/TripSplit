package com.abzal.tripsplit.features.expenses.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.balances.domain.model.Balance
import com.abzal.tripsplit.features.balances.domain.model.Settlement
import com.abzal.tripsplit.features.balances.domain.repository.BalanceRepository
import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.expenses.domain.repository.ExpenseRepository
import com.abzal.tripsplit.features.insights.domain.model.CategorySpending
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TripOverviewUiState(
    val trip: Trip? = null,
    val expenses: List<Expense> = emptyList(),
    val balances: List<Balance> = emptyList(),
    val participants: List<Participant> = emptyList(),
    val settlements: List<Settlement> = emptyList(),
) {
    val currency: String get() = trip?.currency.orEmpty()

    val totalSpent: Double get() = expenses.sumOf { it.amount }

    val perPerson: Double get() = if (participants.isEmpty()) 0.0 else totalSpent / participants.size

    /** Share of debts that was already paid back, 0..100. */
    val settledPercent: Int
        get() {
            val settled = settlements.sumOf { it.amount }
            val unsettled = balances.filter { it.amount > 0 }.sumOf { it.amount }
            val total = settled + unsettled
            return if (total <= 0.0) 0 else (settled / total * 100).toInt()
        }

    val categories: List<CategorySpending>
        get() = expenses.groupBy { it.category }
            .map { (category, items) -> CategorySpending(category, items.sumOf { it.amount }) }
            .sortedByDescending { it.total }

    fun nameOf(participantId: String): String =
        participants.firstOrNull { it.id == participantId }?.name?.substringBefore(' ').orEmpty()
}

class TripOverviewViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val expenseRepository: ExpenseRepository,
    private val balanceRepository: BalanceRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(TripOverviewUiState())
    val uiState: StateFlow<TripOverviewUiState> = _uiState.asStateFlow()

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
            participantRepository.observeParticipants(tripId).collect { value -> _uiState.update { it.copy(participants = value) } }
        }
        viewModelScope.launch {
            balanceRepository.observeSettlements(tripId).collect { value -> _uiState.update { it.copy(settlements = value) } }
        }
    }
}
