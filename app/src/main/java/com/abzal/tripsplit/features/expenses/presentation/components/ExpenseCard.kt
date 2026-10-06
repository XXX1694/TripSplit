package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.preview.sampleExpenses
import com.abzal.tripsplit.features.expenses.domain.model.Expense

/** One expense as a tappable card; the item of the expense history list. */
@Composable
fun ExpenseCard(
    expense: Expense,
    payerName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(modifier = modifier, onClick = onClick) {
        ExpenseRow(expense = expense, payerName = payerName, onClick = onClick)
    }
}

@ThemePreviews
@Composable
private fun ExpenseCardPreview() {
    AppPreview {
        ExpenseCard(
            expense = sampleExpenses.first(),
            payerName = "Maya",
            onClick = {},
        )
    }
}
