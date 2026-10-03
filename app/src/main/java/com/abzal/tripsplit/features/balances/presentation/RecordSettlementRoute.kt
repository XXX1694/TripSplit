package com.abzal.tripsplit.features.balances.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun RecordSettlementRoute(
    onBackClick: () -> Unit,
    onRecorded: () -> Unit,
    viewModel: RecordSettlementViewModel = injectedViewModel { c, h ->
        RecordSettlementViewModel(h, c.tripRepository, c.balanceRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RecordSettlementScreen(
        uiState = uiState,
        onDraftChange = viewModel::onDraftChange,
        onRecordClick = { viewModel.record(onRecorded) },
        onBackClick = onBackClick,
    )
}
