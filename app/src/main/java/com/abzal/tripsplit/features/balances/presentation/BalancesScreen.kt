package com.abzal.tripsplit.features.balances.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.SecondaryButton
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.designsystem.components.TripBottomBar
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.balances.domain.model.Balance
import com.abzal.tripsplit.features.balances.domain.model.Transfer
import com.abzal.tripsplit.features.balances.presentation.components.BalanceSummaryCard
import com.abzal.tripsplit.features.balances.presentation.components.EveryoneBalanceCard
import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun BalancesRoute(
    onTabClick: (TripTab) -> Unit,
    onOptimizedClick: () -> Unit,
    onRecordSettlementClick: () -> Unit,
    viewModel: BalancesViewModel = injectedViewModel { c, h ->
        BalancesViewModel(h, c.tripRepository, c.expenseRepository, c.balanceRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BalancesScreen(
        uiState = uiState,
        onTabClick = onTabClick,
        onOptimizedClick = onOptimizedClick,
        onRecordSettlementClick = onRecordSettlementClick,
    )
}

@Composable
fun BalancesScreen(
    uiState: BalancesUiState,
    onTabClick: (TripTab) -> Unit,
    onOptimizedClick: () -> Unit,
    onRecordSettlementClick: () -> Unit,
) {
    AppScaffold(
        topBar = { AppTopBar(title = "Balances", subtitle = uiState.trip?.name) },
        bottomBar = { TripBottomBar(selected = TripTab.Balances, onTabClick = onTabClick) },
    ) {
        BalanceSummaryCard(uiState)
        EveryoneBalanceCard(uiState)
        InfoBanner(
            text = "Paid minus share equals net. Positive means the person gets money back.",
            icon = Icons.Outlined.Calculate,
            tone = Tone.Warning,
        )
        PrimaryButton(
            text = "Settle up with ${uiState.transfers.size} payments",
            icon = Icons.Outlined.Tune,
            onClick = onOptimizedClick,
            enabled = uiState.transfers.isNotEmpty(),
        )
        SecondaryButton(text = "Record a payment", icon = Icons.Outlined.Payments, onClick = onRecordSettlementClick)
    }
}

@Preview(showBackground = true)
@Composable
private fun BalancesScreenPreview() {
    TripSplitTheme {
        BalancesScreen(
            uiState = BalancesUiState(
                trip = Trip("1", "Lisbon Friends 2026", "EUR"),
                participants = listOf(Participant("a", "1", "Maya Kim"), Participant("b", "1", "Leo Evans")),
                expenses = listOf(Expense(tripId = "1", title = "Dinner", amount = 200.0, currency = "EUR", paidById = "a", participantIds = listOf("a", "b"))),
                balances = listOf(Balance("a", 100.0), Balance("b", -100.0)),
                transfers = listOf(Transfer("b", "a", 100.0)),
            ),
            onTabClick = {},
            onOptimizedClick = {},
            onRecordSettlementClick = {},
        )
    }
}
