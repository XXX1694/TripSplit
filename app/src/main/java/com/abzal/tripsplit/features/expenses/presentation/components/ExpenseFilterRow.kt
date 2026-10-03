package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppChip
import com.abzal.tripsplit.core.designsystem.components.AppDropdownChip
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.expenses.presentation.ExpenseHistoryUiState
import com.abzal.tripsplit.features.expenses.presentation.sampleExpenseHistoryUiState

/** Scrollable row: All, Category, Payer, Currency. */
@Composable
fun ExpenseFilterRow(
    uiState: ExpenseHistoryUiState,
    onClearFilters: () -> Unit,
    onCategoryFilter: (String?) -> Unit,
    onPayerFilter: (participantId: String?) -> Unit,
    onCurrencyFilter: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        AppChip(text = "All", selected = !uiState.hasFilters, onClick = onClearFilters)
        AppDropdownChip(
            title = "Category",
            options = expenseCategories,
            selected = uiState.categoryFilter,
            onSelect = onCategoryFilter,
        )
        AppDropdownChip(
            title = "Payer",
            options = uiState.participants.map { it.name },
            selected = uiState.participants.firstOrNull { it.id == uiState.payerFilter }?.name,
            onSelect = { name -> onPayerFilter(uiState.participants.firstOrNull { it.name == name }?.id) },
        )
        AppDropdownChip(
            title = "Currency",
            options = uiState.availableCurrencies,
            selected = uiState.currencyFilter,
            onSelect = onCurrencyFilter,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpenseFilterRowPreview() {
    AppPreview {
        ExpenseFilterRow(
            uiState = sampleExpenseHistoryUiState,
            onClearFilters = {},
            onCategoryFilter = {},
            onPayerFilter = {},
            onCurrencyFilter = {},
        )
    }
}
