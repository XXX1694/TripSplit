package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.material.icons.outlined.Receipt
import com.abzal.tripsplit.core.designsystem.components.EmptyState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.abzal.tripsplit.core.designsystem.components.AppFab
import com.abzal.tripsplit.core.designsystem.components.AppLazyScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.TripBottomBar
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseCard
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseDayHeader
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseFilterRow
import com.abzal.tripsplit.features.participants.presentation.components.label

@Composable
fun ExpenseHistoryScreen(
    uiState: ExpenseHistoryUiState,
    onQueryChange: (String) -> Unit,
    onCategoryFilter: (String?) -> Unit,
    onPayerFilter: (String?) -> Unit,
    onCurrencyFilter: (String?) -> Unit,
    onClearFilters: () -> Unit,
    onTabClick: (TripTab) -> Unit,
    onExpenseClick: (String) -> Unit,
    onAddExpenseClick: () -> Unit,
) {
    var isSearchOpen by remember { mutableStateOf(false) }

    AppLazyScaffold(
        topBar = {
            AppTopBar(
                title = "Expenses",
                onBackClick = { onTabClick(TripTab.Overview) },
                subtitle = "${uiState.filteredExpenses.size} entries · ${formatMoney(uiState.total, uiState.tripCurrency)}",
                actions = {
                    IconButton(onClick = {
                        isSearchOpen = !isSearchOpen
                        if (!isSearchOpen) onQueryChange("")
                    }) {
                        Icon(
                            imageVector = if (isSearchOpen) Icons.Outlined.Close else Icons.Outlined.Search,
                            contentDescription = "Search",
                        )
                    }
                },
            )
        },
        bottomBar = { TripBottomBar(selected = TripTab.Expenses, onTabClick = onTabClick) },
        floatingActionButton = {
            AppFab(text = "Add expense", icon = Icons.Outlined.Add, onClick = onAddExpenseClick)
        },
        hasFab = true,
    ) {
        if (isSearchOpen) {
            item {
                AppTextField(
                    value = uiState.query,
                    onValueChange = onQueryChange,
                    label = "Search expenses",
                    leadingIcon = Icons.Outlined.Search,
                )
            }
        }
        item { ExpenseFilterRow(uiState, onClearFilters, onCategoryFilter, onPayerFilter, onCurrencyFilter) }

        if (uiState.groups.isEmpty()) {
            item {
                EmptyState(
                    title = "No expenses found",
                    message = "Try another filter or add the first expense of this trip.",
                    icon = Icons.Outlined.Receipt,
                    actionText = "Add expense",
                    onActionClick = onAddExpenseClick,
                )
            }
        }
        uiState.groups.forEach { group ->
            item(key = "day-${group.title}") { ExpenseDayHeader(group = group, currency = uiState.tripCurrency) }
            items(group.expenses, key = { it.id }) { expense ->
                ExpenseCard(
                    expense = expense,
                    payerName = uiState.nameOf(expense.paidById),
                    onClick = { onExpenseClick(expense.id) },
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun ExpenseHistoryScreenPreview() {
    AppPreview {
        ExpenseHistoryScreen(
            uiState = sampleExpenseHistoryUiState,
            onQueryChange = {},
            onCategoryFilter = {},
            onPayerFilter = {},
            onCurrencyFilter = {},
            onClearFilters = {},
            onTabClick = {},
            onExpenseClick = {},
            onAddExpenseClick = {},
        )
    }
}
