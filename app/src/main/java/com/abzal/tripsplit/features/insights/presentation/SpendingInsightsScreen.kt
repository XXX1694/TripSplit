package com.abzal.tripsplit.features.insights.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.TripBottomBar
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.insights.domain.model.CategorySpending
import com.abzal.tripsplit.features.insights.presentation.components.CategoryBreakdownCard
import com.abzal.tripsplit.features.insights.presentation.components.CurrencyTotalsCard
import com.abzal.tripsplit.features.insights.presentation.components.DailySpendingCard
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun SpendingInsightsRoute(
    onTabClick: (TripTab) -> Unit,
    viewModel: SpendingInsightsViewModel = injectedViewModel { c, h ->
        SpendingInsightsViewModel(h, c.tripRepository, c.expenseRepository, c.insightsRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SpendingInsightsScreen(uiState = uiState, onTabClick = onTabClick)
}

@Composable
fun SpendingInsightsScreen(
    uiState: SpendingInsightsUiState,
    onTabClick: (TripTab) -> Unit,
) {
    AppScaffold(
        topBar = { AppTopBar(title = "Insights", subtitle = uiState.trip?.name) },
        bottomBar = { TripBottomBar(selected = TripTab.Insights, onTabClick = onTabClick) },
    ) {
        DailySpendingCard(uiState)
        CategoryBreakdownCard(uiState)
        if (uiState.currencyTotals.size > 1) CurrencyTotalsCard(uiState)
    }
}

@Preview(showBackground = true)
@Composable
private fun SpendingInsightsScreenPreview() {
    val day = 86_400_000L
    val now = System.currentTimeMillis()
    fun expense(amount: Double, category: String, daysAgo: Long) = Expense(
        tripId = "1", title = category, amount = amount, currency = "EUR",
        paidById = "a", participantIds = listOf("a"), category = category, dateMillis = now - daysAgo * day,
    )
    TripSplitTheme {
        SpendingInsightsScreen(
            uiState = SpendingInsightsUiState(
                trip = Trip("1", "Lisbon Friends 2026", "EUR"),
                expenses = listOf(expense(534.0, "Stay", 0), expense(377.0, "Food", 1), expense(220.0, "Transit", 2)),
                spending = listOf(CategorySpending("Stay", 534.0), CategorySpending("Food", 377.0), CategorySpending("Transit", 220.0)),
            ),
            onTabClick = {},
        )
    }
}
