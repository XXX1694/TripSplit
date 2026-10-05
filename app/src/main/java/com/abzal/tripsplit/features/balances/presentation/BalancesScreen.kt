package com.abzal.tripsplit.features.balances.presentation

import androidx.compose.material.icons.outlined.Balance
import com.abzal.tripsplit.core.designsystem.components.EmptyState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.SecondaryButton
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.designsystem.components.TripBottomBar
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
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
        topBar = { AppTopBar(title = "Balances", subtitle = uiState.trip?.name, onBackClick = { onTabClick(TripTab.Overview) }) },
        bottomBar = { TripBottomBar(selected = TripTab.Balances, onTabClick = onTabClick) },
    ) {
        BalanceSummaryCard(uiState)
        if (uiState.balances.isEmpty()) {
            EmptyState(
                title = "No balances yet",
                message = "Add participants and expenses to see who owes whom.",
                icon = Icons.Outlined.Balance,
            )
        } else {
            EveryoneBalanceCard(uiState)
        }
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

@ThemePreviews
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
