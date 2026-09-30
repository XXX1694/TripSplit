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
fun EditExpenseRoute(
    onBackClick: () -> Unit,
    onSaved: () -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
    viewModel: EditExpenseViewModel = injectedViewModel { c, h -> EditExpenseViewModel(h, c.expenseRepository, c.participantRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    EditExpenseScreen(
        uiState = uiState,
        onSaveClick = { updated -> viewModel.save(updated, onSaved) },
        onBackClick = onBackClick,
        onPickCurrencyClick = onPickCurrencyClick,
        pickedCurrency = pickedCurrency,
    )
}

@Composable
fun EditExpenseScreen(
    uiState: EditExpenseUiState,
    onSaveClick: (Expense) -> Unit,
    onBackClick: () -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "EditExpense")
    }
}
