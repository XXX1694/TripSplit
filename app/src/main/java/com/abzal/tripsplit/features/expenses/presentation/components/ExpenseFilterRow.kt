package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppChip
import com.abzal.tripsplit.core.designsystem.components.AppDropdownChip
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
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
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.space8),
    ) {
        item { AppChip(text = "All", selected = !uiState.hasFilters, onClick = onClearFilters) }
        item {
            AppDropdownChip(
                title = "Category",
                options = expenseCategories,
                selected = uiState.categoryFilter,
                onSelect = onCategoryFilter,
            )
        }
        item {
            AppDropdownChip(
                title = "Payer",
                options = uiState.participants.map { it.name },
                selected = uiState.participants.firstOrNull { it.id == uiState.payerFilter }?.name,
                onSelect = { name -> onPayerFilter(uiState.participants.firstOrNull { it.name == name }?.id) },
            )
        }
        item {
            AppDropdownChip(
                title = "Currency",
                options = uiState.availableCurrencies,
                selected = uiState.currencyFilter,
                onSelect = onCurrencyFilter,
            )
        }
    }
}

@ThemePreviews
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
