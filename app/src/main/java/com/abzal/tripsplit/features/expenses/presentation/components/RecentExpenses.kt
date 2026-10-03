package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.expenses.presentation.TripOverviewUiState
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseRow
import com.abzal.tripsplit.features.expenses.presentation.sampleTripOverviewUiState

@Composable
fun RecentExpenses(
    uiState: TripOverviewUiState,
    onExpenseClick: (String) -> Unit,
    onViewAllClick: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        SectionHeader(
            title = "Recent expenses",
            actionText = "View all ${uiState.expenses.size}",
            onActionClick = onViewAllClick,
        )
        uiState.expenses.take(3).forEach { expense ->
            ExpenseRow(
                expense = expense,
                payerName = uiState.nameOf(expense.paidById),
                onClick = { onExpenseClick(expense.id) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentExpensesPreview() {
    AppPreview {
        RecentExpenses(
            uiState = sampleTripOverviewUiState,
            onExpenseClick = {},
            onViewAllClick = {},
        )
    }
}
