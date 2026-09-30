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
fun ExpenseHistoryRoute(
    onBackClick: () -> Unit,
    onExpenseClick: (String) -> Unit,
    onAddExpenseClick: () -> Unit,
    viewModel: ExpenseHistoryViewModel = injectedViewModel { c, h -> ExpenseHistoryViewModel(h, c.expenseRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ExpenseHistoryScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onExpenseClick = onExpenseClick,
        onAddExpenseClick = onAddExpenseClick,
    )
}

@Composable
fun ExpenseHistoryScreen(
    uiState: ExpenseHistoryUiState,
    onBackClick: () -> Unit,
    onExpenseClick: (String) -> Unit,
    onAddExpenseClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "ExpenseHistory")
    }
}
