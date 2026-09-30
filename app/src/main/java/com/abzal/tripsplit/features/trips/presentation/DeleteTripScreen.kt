package com.abzal.tripsplit.features.trips.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.autharization.domain.model.*
import com.abzal.tripsplit.features.balances.domain.model.*
import com.abzal.tripsplit.features.expenses.domain.model.*
import com.abzal.tripsplit.features.insights.domain.model.*
import com.abzal.tripsplit.features.participants.domain.model.*
import com.abzal.tripsplit.features.trips.domain.model.*

@Composable
fun DeleteTripRoute(
    onCancelClick: () -> Unit,
    onDeleted: () -> Unit,
    viewModel: DeleteTripViewModel = injectedViewModel { c, h -> DeleteTripViewModel(h, c.tripRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DeleteTripScreen(
        uiState = uiState,
        onConfirmClick = { viewModel.delete(onDeleted) },
        onCancelClick = onCancelClick,
    )
}

@Composable
fun DeleteTripScreen(
    uiState: DeleteTripUiState,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "DeleteTrip")
    }
}
