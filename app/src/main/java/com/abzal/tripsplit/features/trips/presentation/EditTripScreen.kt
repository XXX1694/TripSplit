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
fun EditTripRoute(
    onBackClick: () -> Unit,
    onSaved: () -> Unit,
    onDeleteClick: () -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
    viewModel: EditTripViewModel = injectedViewModel { c, h -> EditTripViewModel(h, c.tripRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    EditTripScreen(
        uiState = uiState,
        onSaveClick = { name, currency, start, end -> viewModel.save(name, currency, start, end, onSaved) },
        onDeleteClick = onDeleteClick,
        onBackClick = onBackClick,
        onPickCurrencyClick = onPickCurrencyClick,
        pickedCurrency = pickedCurrency,
    )
}

@Composable
fun EditTripScreen(
    uiState: EditTripUiState,
    onSaveClick: (String, String, Long?, Long?) -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "EditTrip")
    }
}
