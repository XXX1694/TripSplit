package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.components.AppFab
import com.abzal.tripsplit.core.designsystem.components.AppOverflowMenu
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.MenuItem
import com.abzal.tripsplit.core.designsystem.components.TripBottomBar
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatTripDates
import com.abzal.tripsplit.features.expenses.presentation.components.BalancesPreviewCard
import com.abzal.tripsplit.features.expenses.presentation.components.CategorySpendingCard
import com.abzal.tripsplit.features.expenses.presentation.components.RecentExpenses
import com.abzal.tripsplit.features.expenses.presentation.components.TripTotalCard

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
        Spacer(Modifier.height(Sizes.fabClearance))
    }
}

@ThemePreviews
@Composable
private fun TripOverviewScreenPreview() {
    AppPreview {
        TripOverviewScreen(
            uiState = sampleTripOverviewUiState,
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
