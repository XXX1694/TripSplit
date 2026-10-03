package com.abzal.tripsplit.features.insights.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.core.util.startOfDay
import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.expenses.domain.repository.ExpenseRepository
import com.abzal.tripsplit.features.insights.domain.model.CategorySpending
import com.abzal.tripsplit.features.insights.domain.repository.InsightsRepository
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Everything spent on one day. */
data class DaySpending(val dayStartMillis: Long, val total: Double)

data class SpendingInsightsUiState(
    val trip: Trip? = null,
    val spending: List<CategorySpending> = emptyList(),
    val expenses: List<Expense> = emptyList(),
) {
    val currency: String get() = trip?.currency.orEmpty()

    val totalSpent: Double get() = expenses.sumOf { it.amount }

    /** Spending per day, oldest first. */
    val days: List<DaySpending>
        get() = expenses.groupBy { startOfDay(it.dateMillis) }
            .map { (day, items) -> DaySpending(day, items.sumOf { it.amount }) }
            .sortedBy { it.dayStartMillis }

    val dailyAverage: Double get() = if (days.isEmpty()) 0.0 else totalSpent / days.size

    val peakDay: DaySpending? get() = days.maxByOrNull { it.total }

    /** Totals of expenses grouped by their own currency. */
    val currencyTotals: List<Pair<String, Double>>
        get() = expenses.groupBy { it.currency }.map { (code, items) -> code to items.sumOf { it.amount } }
}

class SpendingInsightsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val expenseRepository: ExpenseRepository,
    private val insightsRepository: InsightsRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(SpendingInsightsUiState())
    val uiState: StateFlow<SpendingInsightsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tripRepository.observeTrip(tripId).collect { value -> _uiState.update { it.copy(trip = value) } }
        }
        viewModelScope.launch {
            expenseRepository.observeExpenses(tripId).collect { value -> _uiState.update { it.copy(expenses = value) } }
        }
        viewModelScope.launch {
            insightsRepository.observeSpendingByCategory(tripId).collect { value -> _uiState.update { it.copy(spending = value) } }
        }
    }
}
