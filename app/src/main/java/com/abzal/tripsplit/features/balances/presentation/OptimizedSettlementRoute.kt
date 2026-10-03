package com.abzal.tripsplit.features.balances.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun OptimizedSettlementRoute(
    onBackClick: () -> Unit,
    onRecordSettlementClick: () -> Unit,
    viewModel: OptimizedSettlementViewModel = injectedViewModel { c, h ->
        OptimizedSettlementViewModel(h, c.tripRepository, c.balanceRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    OptimizedSettlementScreen(
        uiState = uiState,
        onMarkPaidClick = viewModel::markPaid,
        onBackClick = onBackClick,
        onRecordSettlementClick = onRecordSettlementClick,
    )
}
