package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppFab
import com.abzal.tripsplit.core.designsystem.components.AppOverflowMenu
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.MenuItem
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.designsystem.components.TripBottomBar
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.core.util.formatTripDates
import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.expenses.presentation.components.BalancesPreviewCard
import com.abzal.tripsplit.features.expenses.presentation.components.CategorySpendingCard
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseRow
import com.abzal.tripsplit.features.expenses.presentation.components.TripTotalCard
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun TripOverviewRoute(
    onBackClick: () -> Unit,
    onEditTripClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onExpenseClick: (String) -> Unit,
    onExpenseHistoryClick: () -> Unit,
    onBalancesClick: () -> Unit,
    onParticipantsClick: () -> Unit,
    onInsightsClick: () -> Unit,
    viewModel: TripOverviewViewModel = injectedViewModel { c, h ->
        TripOverviewViewModel(h, c.tripRepository, c.expenseRepository, c.balanceRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TripOverviewScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onEditTripClick = onEditTripClick,
        onAddExpenseClick = onAddExpenseClick,
        onExpenseClick = onExpenseClick,
        onExpenseHistoryClick = onExpenseHistoryClick,
        onBalancesClick = onBalancesClick,
        onParticipantsClick = onParticipantsClick,
        onInsightsClick = onInsightsClick,
    )
}

@Composable
fun TripOverviewScreen(
    uiState: TripOverviewUiState,
    onBackClick: () -> Unit,
    onEditTripClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onExpenseClick: (String) -> Unit,
    onExpenseHistoryClick: () -> Unit,
    onBalancesClick: () -> Unit,
    onParticipantsClick: () -> Unit,
    onInsightsClick: () -> Unit,
) {
    val trip = uiState.trip

    AppScaffold(
        topBar = {
            AppTopBar(
                title = trip?.name.orEmpty(),
                subtitle = trip?.let { "${formatTripDates(it.startDateMillis, it.endDateMillis)} · ${it.currency}" },
                onBackClick = onBackClick,
                actions = {
                    AppOverflowMenu(
                        listOf(MenuItem("Edit trip", onEditTripClick), MenuItem("Participants", onParticipantsClick)),
                    )
                },
            )
        },
        bottomBar = {
            TripBottomBar(
                selected = TripTab.Overview,
                onTabClick = { tab ->
                    when (tab) {
                        TripTab.Overview -> Unit
                        TripTab.Expenses -> onExpenseHistoryClick()
                        TripTab.Balances -> onBalancesClick()
                        TripTab.Insights -> onInsightsClick()
                    }
                },
            )
        },
        floatingActionButton = {
            AppFab(text = "Add expense", icon = Icons.Outlined.Add, onClick = onAddExpenseClick)
        },
    ) {
        TripTotalCard(uiState)
        CategorySpendingCard(uiState, onInsightsClick = onInsightsClick)
        BalancesPreviewCard(uiState, onSeeAllClick = onBalancesClick)
        RecentExpenses(uiState, onExpenseClick, onViewAllClick = onExpenseHistoryClick)
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun RecentExpenses(
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
private fun TripOverviewScreenPreview() {
    TripSplitTheme {
        TripOverviewScreen(
            uiState = TripOverviewUiState(
                trip = Trip("1", "Lisbon Friends 2026", "EUR", 1_757_635_200_000, 1_758_067_200_000),
                participants = listOf(Participant("a", "1", "Maya Kim"), Participant("b", "1", "Leo Evans")),
                expenses = listOf(Expense(tripId = "1", title = "Dinner at Prado", amount = 148.0, currency = "EUR", paidById = "a", participantIds = listOf("a", "b"), category = "Food")),
            ),
            onBackClick = {},
            onEditTripClick = {},
            onAddExpenseClick = {},
            onExpenseClick = {},
            onExpenseHistoryClick = {},
            onBalancesClick = {},
            onParticipantsClick = {},
            onInsightsClick = {},
        )
    }
}
