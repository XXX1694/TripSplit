package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppFab
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.TripBottomBar
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.expenses.presentation.components.EmptyHint
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseDaySection
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

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Expenses",
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
    ) {
        if (isSearchOpen) {
            AppTextField(
                value = uiState.query,
                onValueChange = onQueryChange,
                label = "Search expenses",
                leadingIcon = Icons.Outlined.Search,
            )
        }
        ExpenseFilterRow(uiState, onClearFilters, onCategoryFilter, onPayerFilter, onCurrencyFilter)

        if (uiState.groups.isEmpty()) EmptyHint("No expenses found")
        uiState.groups.forEach { group ->
            ExpenseDaySection(group = group, uiState = uiState, onExpenseClick = onExpenseClick)
        }
        Spacer(Modifier.height(Spacing.fabClearance))
    }
}

@Preview(showBackground = true)
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
