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
fun CreateTripRoute(
    onBackClick: () -> Unit,
    onCreated: (String) -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
    viewModel: CreateTripViewModel = injectedViewModel { c, h -> CreateTripViewModel(c.tripRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CreateTripScreen(
        uiState = uiState,
        onSaveClick = { name, currency, start, end -> viewModel.createTrip(name, currency, start, end, onCreated) },
        onBackClick = onBackClick,
        onPickCurrencyClick = onPickCurrencyClick,
        pickedCurrency = pickedCurrency,
    )
}

@Composable
fun CreateTripScreen(
    uiState: CreateTripUiState,
    onSaveClick: (String, String, Long?, Long?) -> Unit,
    onBackClick: () -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "CreateTrip")
    }
}
