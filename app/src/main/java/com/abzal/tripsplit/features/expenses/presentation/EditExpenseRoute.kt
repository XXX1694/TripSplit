package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.core.navigation.pickedCurrency

@Composable
fun EditExpenseRoute(
    onBackClick: () -> Unit,
    onSaved: () -> Unit,
    onDeleted: () -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
    viewModel: EditExpenseViewModel = injectedViewModel { c, h ->
        EditExpenseViewModel(h, c.tripRepository, c.expenseRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(pickedCurrency) {
        if (pickedCurrency != null) viewModel.onCurrencyPicked(pickedCurrency)
    }

    EditExpenseScreen(
        uiState = uiState,
        onDraftChange = viewModel::onDraftChange,
        onSaveClick = { viewModel.save(onSaved) },
        onDeleteClick = { viewModel.delete(onDeleted) },
        onBackClick = onBackClick,
        onPickCurrencyClick = onPickCurrencyClick,
    )
}
