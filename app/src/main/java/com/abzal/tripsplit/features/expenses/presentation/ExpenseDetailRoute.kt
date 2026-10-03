package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun ExpenseDetailRoute(
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleted: () -> Unit,
    viewModel: ExpenseDetailViewModel = injectedViewModel { c, h ->
        ExpenseDetailViewModel(h, c.tripRepository, c.expenseRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ExpenseDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onEditClick = onEditClick,
        onDeleteClick = { viewModel.delete(onDeleted) },
    )
}
