package com.abzal.tripsplit.features.trips.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun DeleteTripRoute(
    onCancelClick: () -> Unit,
    onDeleted: () -> Unit,
    viewModel: DeleteTripViewModel = injectedViewModel { c, h ->
        DeleteTripViewModel(h, c.tripRepository, c.expenseRepository, c.participantRepository, c.balanceRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DeleteTripScreen(
        uiState = uiState,
        onConfirmClick = { viewModel.delete(onDeleted) },
        onCancelClick = onCancelClick,
    )
}
