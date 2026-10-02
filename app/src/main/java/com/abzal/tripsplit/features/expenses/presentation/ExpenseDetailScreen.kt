package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppOverflowMenu
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.MenuItem
import com.abzal.tripsplit.core.designsystem.components.SecondaryButton
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.expenses.presentation.components.DeleteExpenseConfirmation
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseSharesCard
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseSummaryCard
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun ExpenseDetailRoute(
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleted: () -> Unit,
    viewModel: ExpenseDetailViewModel = injectedViewModel { c, h ->
        ExpenseDetailViewModel(h, c.tripRepository, c.expenseRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ExpenseDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onEditClick = onEditClick,
        onDeleteClick = { viewModel.delete(onDeleted) },
    )
}

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

@Preview(showBackground = true)
@Composable
private fun ExpenseDetailScreenPreview() {
    TripSplitTheme {
        ExpenseDetailScreen(
            uiState = ExpenseDetailUiState(
                expense = Expense(tripId = "1", title = "Dinner at Prado", amount = 148.0, currency = "EUR", paidById = "a", participantIds = listOf("a", "b"), category = "Food"),
                trip = Trip("1", "Lisbon Friends 2026", "EUR"),
                participants = listOf(Participant("a", "1", "Maya Kim"), Participant("b", "1", "Leo Evans")),
            ),
            onBackClick = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}
