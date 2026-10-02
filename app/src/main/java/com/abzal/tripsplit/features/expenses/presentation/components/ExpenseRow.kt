package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.IconBadge
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.core.util.formatRelativeDate
import com.abzal.tripsplit.features.expenses.domain.model.Expense

/** "Dinner at Prado / Today · Maya paid · 4 people / €148.00" */
@Composable
fun ExpenseRow(
    expense: Expense,
    payerName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val subtitle = "${formatRelativeDate(expense.dateMillis)} · $payerName paid · ${expense.participantIds.size} people"
    AppListRow(
        title = expense.title,
        subtitle = subtitle,
        modifier = modifier,
        onClick = onClick,
        leading = { IconBadge(icon = categoryIcon(expense.category), tone = categoryTone(expense.category)) },
        trailing = {
            Text(
                text = formatMoney(expense.amount, expense.currency),
                style = MaterialTheme.typography.titleSmall,
                color = AppTheme.colors.textPrimary,
            )
        },
    )
}
