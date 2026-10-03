package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun ExpenseHistoryRoute(
    onTabClick: (TripTab) -> Unit,
    onExpenseClick: (String) -> Unit,
    onAddExpenseClick: () -> Unit,
    viewModel: ExpenseHistoryViewModel = injectedViewModel { c, h ->
        ExpenseHistoryViewModel(h, c.tripRepository, c.expenseRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ExpenseHistoryScreen(
        uiState = uiState,
        onQueryChange = viewModel::onQueryChange,
        onCategoryFilter = viewModel::onCategoryFilter,
        onPayerFilter = viewModel::onPayerFilter,
        onCurrencyFilter = viewModel::onCurrencyFilter,
        onClearFilters = viewModel::clearFilters,
        onTabClick = onTabClick,
        onExpenseClick = onExpenseClick,
        onAddExpenseClick = onAddExpenseClick,
    )
}
