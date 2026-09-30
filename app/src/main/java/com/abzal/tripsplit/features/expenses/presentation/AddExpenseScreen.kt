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
fun AddExpenseRoute(
    onBackClick: () -> Unit,
    onSaved: () -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
    viewModel: AddExpenseViewModel = injectedViewModel { c, h -> AddExpenseViewModel(h, c.expenseRepository, c.participantRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AddExpenseScreen(
        uiState = uiState,
        onSaveClick = { title, amount, currency, paidById, participantIds, category -> viewModel.addExpense(title, amount, currency, paidById, participantIds, category, onSaved) },
        onBackClick = onBackClick,
        onPickCurrencyClick = onPickCurrencyClick,
        pickedCurrency = pickedCurrency,
    )
}

@Composable
fun AddExpenseScreen(
    uiState: AddExpenseUiState,
    onSaveClick: (String, Double, String, String, List<String>, String) -> Unit,
    onBackClick: () -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "AddExpense")
    }
}
