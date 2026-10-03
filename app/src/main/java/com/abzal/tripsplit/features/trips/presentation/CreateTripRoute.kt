package com.abzal.tripsplit.features.trips.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.core.navigation.pickedCurrency

@Composable
fun CreateTripRoute(
    onBackClick: () -> Unit,
    onCreated: (String) -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
    viewModel: CreateTripViewModel = injectedViewModel { c, _ -> CreateTripViewModel(c.tripRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(pickedCurrency) {
        if (pickedCurrency != null) viewModel.onCurrencyPicked(pickedCurrency)
    }

    CreateTripScreen(
        uiState = uiState,
        onDraftChange = viewModel::onDraftChange,
        onSaveClick = { viewModel.createTrip(onCreated) },
        onBackClick = onBackClick,
        onPickCurrencyClick = onPickCurrencyClick,
    )
}
