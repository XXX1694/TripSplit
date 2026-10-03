package com.abzal.tripsplit.features.expenses.presentation

import com.abzal.tripsplit.core.preview.sampleBalances
import com.abzal.tripsplit.core.preview.sampleCurrencies
import com.abzal.tripsplit.core.preview.sampleExpenses
import com.abzal.tripsplit.core.preview.sampleParticipants
import com.abzal.tripsplit.core.preview.sampleSettlements
import com.abzal.tripsplit.core.preview.sampleTrip

// Sample UI states for @Preview.

val sampleExpenseDraft: ExpenseDraft = sampleExpenses.first().toDraft()

val sampleExpenseDayGroup = ExpenseDayGroup(
    title = "TODAY · 15 SEP",
    total = sampleExpenses.take(2).sumOf { it.amount },
    expenses = sampleExpenses.take(2),
)

val sampleTripOverviewUiState = TripOverviewUiState(
    trip = sampleTrip,
    expenses = sampleExpenses,
    balances = sampleBalances,
    participants = sampleParticipants,
    settlements = sampleSettlements,
)

val sampleExpenseHistoryUiState = ExpenseHistoryUiState(
    trip = sampleTrip,
    expenses = sampleExpenses,
    participants = sampleParticipants,
)

val sampleExpenseDetailUiState = ExpenseDetailUiState(
    expense = sampleExpenses.first(),
    trip = sampleTrip,
    participants = sampleParticipants,
)

val sampleAddExpenseUiState = AddExpenseUiState(
    trip = sampleTrip,
    draft = ExpenseDraft(currency = "EUR", paidById = "p1", participantIds = sampleParticipants.map { it.id }.toSet()),
    participants = sampleParticipants,
)

val sampleEditExpenseUiState = EditExpenseUiState(
    trip = sampleTrip,
    draft = sampleExpenseDraft,
    participants = sampleParticipants,
    isLoaded = true,
)

val sampleCurrencyPickerUiState = CurrencyPickerUiState(currencies = sampleCurrencies, selectedCode = "USD")
