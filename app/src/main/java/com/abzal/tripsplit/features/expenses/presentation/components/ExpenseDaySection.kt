package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.expenses.presentation.ExpenseDayGroup
import com.abzal.tripsplit.features.expenses.presentation.ExpenseHistoryUiState
import com.abzal.tripsplit.features.expenses.presentation.sampleExpenseDayGroup
import com.abzal.tripsplit.features.expenses.presentation.sampleExpenseHistoryUiState

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

@ThemePreviews
@Composable
private fun ExpenseDaySectionPreview() {
    AppPreview {
        ExpenseDaySection(
            group = sampleExpenseDayGroup,
            uiState = sampleExpenseHistoryUiState,
            onExpenseClick = {},
        )
    }
}
