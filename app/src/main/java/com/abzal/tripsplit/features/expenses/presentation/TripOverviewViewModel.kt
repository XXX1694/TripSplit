package com.abzal.tripsplit.features.expenses.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.features.autharization.domain.model.*
import com.abzal.tripsplit.features.balances.domain.model.*
import com.abzal.tripsplit.features.balances.domain.repository.BalanceRepository
import com.abzal.tripsplit.features.expenses.domain.model.*
import com.abzal.tripsplit.features.expenses.domain.repository.ExpenseRepository
import com.abzal.tripsplit.features.insights.domain.model.*
import com.abzal.tripsplit.features.participants.domain.model.*
import com.abzal.tripsplit.features.trips.domain.model.*
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
)

class TripOverviewViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val expenseRepository: ExpenseRepository,
    private val balanceRepository: BalanceRepository,
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
    }
}
