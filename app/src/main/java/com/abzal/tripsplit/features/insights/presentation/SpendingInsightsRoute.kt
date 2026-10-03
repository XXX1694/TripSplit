package com.abzal.tripsplit.features.insights.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.di.injectedViewModel

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
