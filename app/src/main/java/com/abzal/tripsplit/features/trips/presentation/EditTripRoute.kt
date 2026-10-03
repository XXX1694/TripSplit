package com.abzal.tripsplit.features.trips.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.core.navigation.pickedCurrency

@Composable
fun EditTripRoute(
    onBackClick: () -> Unit,
    onSaved: () -> Unit,
    onDeleteClick: () -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
    viewModel: EditTripViewModel = injectedViewModel { c, h -> EditTripViewModel(h, c.tripRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(pickedCurrency) {
        if (pickedCurrency != null) viewModel.onCurrencyPicked(pickedCurrency)
    }

    EditTripScreen(
        uiState = uiState,
        onDraftChange = viewModel::onDraftChange,
        onSaveClick = { viewModel.save(onSaved) },
        onDeleteClick = onDeleteClick,
        onBackClick = onBackClick,
        onPickCurrencyClick = onPickCurrencyClick,
    )
}
