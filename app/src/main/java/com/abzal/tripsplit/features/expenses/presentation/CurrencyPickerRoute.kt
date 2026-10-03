package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun CurrencyPickerRoute(
    onBackClick: () -> Unit,
    onCurrencySelected: (String) -> Unit,
    viewModel: CurrencyPickerViewModel = injectedViewModel { c, _ -> CurrencyPickerViewModel(c.currencyRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CurrencyPickerScreen(
        uiState = uiState,
        onQueryChange = viewModel::onQueryChange,
        onCurrencyClick = viewModel::onCurrencySelect,
        onConfirmClick = { uiState.selectedCode?.let(onCurrencySelected) },
        onBackClick = onBackClick,
    )
}
