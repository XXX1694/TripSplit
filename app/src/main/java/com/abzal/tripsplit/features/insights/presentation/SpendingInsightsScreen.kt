package com.abzal.tripsplit.features.insights.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.TripBottomBar
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.insights.presentation.components.CategoryBreakdownCard
import com.abzal.tripsplit.features.insights.presentation.components.CurrencyTotalsCard
import com.abzal.tripsplit.features.insights.presentation.components.DailySpendingCard

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
    AppPreview {
        SpendingInsightsScreen(
            uiState = sampleSpendingInsightsUiState,
            onTabClick = {},
        )
    }
}
