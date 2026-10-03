package com.abzal.tripsplit.features.balances.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.SecondaryButton
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.designsystem.components.TripBottomBar
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.balances.presentation.components.BalanceSummaryCard
import com.abzal.tripsplit.features.balances.presentation.components.EveryoneBalanceCard
import com.abzal.tripsplit.features.participants.presentation.components.tone

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
    AppPreview {
        BalancesScreen(
            uiState = sampleBalancesUiState,
            onTabClick = {},
            onOptimizedClick = {},
            onRecordSettlementClick = {},
        )
    }
}
