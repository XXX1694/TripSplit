package com.abzal.tripsplit.features.balances.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun BalancesRoute(
    onTabClick: (TripTab) -> Unit,
    onOptimizedClick: () -> Unit,
    onRecordSettlementClick: () -> Unit,
    viewModel: BalancesViewModel = injectedViewModel { c, h ->
        BalancesViewModel(h, c.tripRepository, c.expenseRepository, c.balanceRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BalancesScreen(
        uiState = uiState,
        onTabClick = onTabClick,
        onOptimizedClick = onOptimizedClick,
        onRecordSettlementClick = onRecordSettlementClick,
    )
}
