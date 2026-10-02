package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppChip
import com.abzal.tripsplit.core.designsystem.components.AppDropdownChip
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.expenses.presentation.ExpenseDayGroup
import com.abzal.tripsplit.features.expenses.presentation.ExpenseHistoryUiState

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

/** Day title with the day total and a card with that day's expenses. */
@Composable
fun ExpenseDaySection(
    group: ExpenseDayGroup,
    uiState: ExpenseHistoryUiState,
    onExpenseClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(group.title, style = MaterialTheme.typography.labelSmall, color = AppTheme.colors.textSecondary)
            Text(
                text = formatMoney(group.total, uiState.tripCurrency),
                style = MaterialTheme.typography.labelMedium,
                color = AppTheme.colors.textSecondary,
            )
        }
        AppCard(verticalArrangement = Arrangement.spacedBy(Spacing.xxs)) {
            group.expenses.forEach { expense ->
                ExpenseRow(
                    expense = expense,
                    payerName = uiState.nameOf(expense.paidById),
                    onClick = { onExpenseClick(expense.id) },
                )
            }
        }
    }
}
