package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun TripOverviewRoute(
    onBackClick: () -> Unit,
    onEditTripClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onExpenseClick: (String) -> Unit,
    onExpenseHistoryClick: () -> Unit,
    onBalancesClick: () -> Unit,
    onParticipantsClick: () -> Unit,
    onInsightsClick: () -> Unit,
    viewModel: TripOverviewViewModel = injectedViewModel { c, h ->
        TripOverviewViewModel(h, c.tripRepository, c.expenseRepository, c.balanceRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TripOverviewScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onEditTripClick = onEditTripClick,
        onAddExpenseClick = onAddExpenseClick,
        onExpenseClick = onExpenseClick,
        onExpenseHistoryClick = onExpenseHistoryClick,
        onBalancesClick = onBalancesClick,
        onParticipantsClick = onParticipantsClick,
        onInsightsClick = onInsightsClick,
    )
}
