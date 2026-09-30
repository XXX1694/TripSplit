package com.abzal.tripsplit.features.expenses.presentation

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
fun CurrencyPickerRoute(
    onBackClick: () -> Unit,
    onCurrencySelected: (String) -> Unit,
    viewModel: CurrencyPickerViewModel = injectedViewModel { c, h -> CurrencyPickerViewModel(c.currencyRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CurrencyPickerScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onCurrencyClick = onCurrencySelected,
    )
}

@Composable
fun CurrencyPickerScreen(
    uiState: CurrencyPickerUiState,
    onBackClick: () -> Unit,
    onCurrencyClick: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "CurrencyPicker")
    }
}
