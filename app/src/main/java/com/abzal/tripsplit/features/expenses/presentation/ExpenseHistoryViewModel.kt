package com.abzal.tripsplit.features.expenses.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.core.util.formatDayHeader
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

/** Expenses of one day with their sum. */
data class ExpenseDayGroup(
    val title: String,
    val total: Double,
    val expenses: List<Expense>,
)

data class ExpenseHistoryUiState(
    val trip: Trip? = null,
    val expenses: List<Expense> = emptyList(),
    val participants: List<Participant> = emptyList(),
    val query: String = "",
    val categoryFilter: String? = null,
    val payerFilter: String? = null,
    val currencyFilter: String? = null,
) {
    val tripCurrency: String get() = trip?.currency.orEmpty()

    val hasFilters: Boolean
        get() = categoryFilter != null || payerFilter != null || currencyFilter != null

    val filteredExpenses: List<Expense>
        get() = expenses.filter { expense ->
            expense.title.contains(query, ignoreCase = true) &&
                (categoryFilter == null || expense.category == categoryFilter) &&
                (payerFilter == null || expense.paidById == payerFilter) &&
                (currencyFilter == null || expense.currency == currencyFilter)
        }

    val total: Double get() = filteredExpenses.sumOf { it.amount }

    val groups: List<ExpenseDayGroup>
        get() = filteredExpenses
            .groupBy { formatDayHeader(it.dateMillis) }
            .map { (title, items) -> ExpenseDayGroup(title, items.sumOf { it.amount }, items) }

    val availableCurrencies: List<String> get() = expenses.map { it.currency }.distinct()

    fun nameOf(participantId: String): String =
        participants.firstOrNull { it.id == participantId }?.name?.substringBefore(' ').orEmpty()
}

class ExpenseHistoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val expenseRepository: ExpenseRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(ExpenseHistoryUiState())
    val uiState: StateFlow<ExpenseHistoryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tripRepository.observeTrip(tripId).collect { value -> _uiState.update { it.copy(trip = value) } }
        }
        viewModelScope.launch {
            expenseRepository.observeExpenses(tripId).collect { value -> _uiState.update { it.copy(expenses = value) } }
        }
        viewModelScope.launch {
            participantRepository.observeParticipants(tripId).collect { value -> _uiState.update { it.copy(participants = value) } }
        }
    }

    fun onQueryChange(query: String) = _uiState.update { it.copy(query = query) }

    fun onCategoryFilter(category: String?) = _uiState.update { it.copy(categoryFilter = category) }

    fun onPayerFilter(participantId: String?) = _uiState.update { it.copy(payerFilter = participantId) }

    fun onCurrencyFilter(currency: String?) = _uiState.update { it.copy(currencyFilter = currency) }

    fun clearFilters() = _uiState.update { it.copy(categoryFilter = null, payerFilter = null, currencyFilter = null) }
}
