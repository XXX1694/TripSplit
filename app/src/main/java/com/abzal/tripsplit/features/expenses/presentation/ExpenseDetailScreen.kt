package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.abzal.tripsplit.core.designsystem.components.AppOverflowMenu
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.MenuItem
import com.abzal.tripsplit.core.designsystem.components.SecondaryButton
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.expenses.presentation.components.DeleteExpenseConfirmation
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseSharesCard
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseSummaryCard

@Composable
fun ExpenseDetailScreen(
    uiState: ExpenseDetailUiState,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var isDeleteConfirmOpen by remember { mutableStateOf(false) }
    val expense = uiState.expense

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Expense details",
                subtitle = uiState.trip?.name,
                onBackClick = onBackClick,
                actions = {
                    AppOverflowMenu(
                        listOf(
                            MenuItem("Edit expense", onEditClick),
                            MenuItem("Delete expense") { isDeleteConfirmOpen = true },
                        ),
                    )
                },
            )
        },
    ) {
        if (expense != null) {
            ExpenseSummaryCard(expense)
            ExpenseSharesCard(uiState)
            SecondaryButton(text = "Edit expense", icon = Icons.Outlined.Edit, onClick = onEditClick)
            if (isDeleteConfirmOpen) {
                DeleteExpenseConfirmation(
                    expenseTitle = expense.title,
                    participantCount = uiState.participants.size,
                    onCancelClick = { isDeleteConfirmOpen = false },
                    onDeleteClick = onDeleteClick,
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun ExpenseDetailScreenPreview() {
    AppPreview {
        ExpenseDetailScreen(
            uiState = sampleExpenseDetailUiState,
            onBackClick = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}
