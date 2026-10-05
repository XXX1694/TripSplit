package com.abzal.tripsplit.features.insights.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PieChart
import com.abzal.tripsplit.core.designsystem.components.EmptyState
import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.TripBottomBar
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.insights.presentation.components.CategoryBreakdownCard
import com.abzal.tripsplit.features.insights.presentation.components.CurrencyTotalsCard
import com.abzal.tripsplit.features.insights.presentation.components.DailySpendingCard

@Composable
fun SpendingInsightsScreen(
    uiState: SpendingInsightsUiState,
    onTabClick: (TripTab) -> Unit,
) {
    AppScaffold(
        topBar = { AppTopBar(title = "Insights", subtitle = uiState.trip?.name, onBackClick = { onTabClick(TripTab.Overview) }) },
        bottomBar = { TripBottomBar(selected = TripTab.Insights, onTabClick = onTabClick) },
    ) {
        if (uiState.expenses.isEmpty()) {
            EmptyState(
                title = "No spending yet",
                message = "Insights appear after the first expense is added.",
                icon = Icons.Outlined.PieChart,
            )
        }
        DailySpendingCard(uiState)
        CategoryBreakdownCard(uiState)
        if (uiState.currencyTotals.size > 1) CurrencyTotalsCard(uiState)
    }
}

@ThemePreviews
@Composable
private fun SpendingInsightsScreenPreview() {
    AppPreview {
        SpendingInsightsScreen(
            uiState = sampleSpendingInsightsUiState,
            onTabClick = {},
        )
    }
}
