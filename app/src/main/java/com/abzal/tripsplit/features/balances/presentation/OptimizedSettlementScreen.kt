package com.abzal.tripsplit.features.balances.presentation

import com.abzal.tripsplit.core.designsystem.components.EmptyState
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.SecondaryButton
import com.abzal.tripsplit.core.designsystem.components.StatusPill
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.balances.domain.model.Transfer
import com.abzal.tripsplit.features.balances.presentation.components.PlanSummaryCard
import com.abzal.tripsplit.features.balances.presentation.components.TransferCard
import com.abzal.tripsplit.features.participants.presentation.components.tone

@Composable
fun OptimizedSettlementScreen(
    uiState: OptimizedSettlementUiState,
    onMarkPaidClick: (Transfer) -> Unit,
    onBackClick: () -> Unit,
    onRecordSettlementClick: () -> Unit,
) {
    val context = LocalContext.current

    AppScaffold(
        topBar = { AppTopBar(title = "Settle up", subtitle = uiState.trip?.name, onBackClick = onBackClick) },
        bottomBar = {
            BottomActionBar {
                PrimaryButton(
                    text = "Share payment plan",
                    icon = Icons.Outlined.Share,
                    onClick = { sharePlan(context, uiState) },
                    enabled = uiState.transfers.isNotEmpty(),
                )
                SecondaryButton(text = "Record another payment", icon = Icons.Outlined.Add, onClick = onRecordSettlementClick)
            }
        },
    ) {
        PlanSummaryCard(uiState)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Recommended plan", style = MaterialTheme.typography.titleMedium, color = AppTheme.colors.text)
            StatusPill(text = uiState.currency, tone = Tone.Accent)
        }
        if (uiState.transfers.isEmpty()) {
            EmptyState(
                title = "Everyone is settled",
                message = "Nobody owes anything, there are no payments to make.",
                icon = Icons.Outlined.CheckCircle,
            )
        }
        uiState.transfers.forEach { transfer ->
            TransferCard(
                fromName = uiState.nameOf(transfer.fromId),
                toName = uiState.nameOf(transfer.toId),
                amountText = formatMoney(transfer.amount, uiState.currency),
                fromToneIndex = uiState.toneIndexOf(transfer.fromId),
                toToneIndex = uiState.toneIndexOf(transfer.toId),
                onMarkPaidClick = { onMarkPaidClick(transfer) },
            )
        }
        InfoBanner(
            text = if (uiState.transfers.isEmpty()) {
                "Everyone is settled. Nobody owes anything."
            } else {
                "These payments exactly clear ${formatMoney(uiState.totalAmount, uiState.currency)} owed and leave every balance at zero."
            },
            icon = Icons.Outlined.CheckCircle,
            tone = Tone.Accent,
        )
    }
}

private fun sharePlan(context: Context, uiState: OptimizedSettlementUiState) {
    val lines = uiState.transfers.joinToString("\n") {
        "${uiState.nameOf(it.fromId)} → ${uiState.nameOf(it.toId)}: ${formatMoney(it.amount, uiState.currency)}"
    }
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "${uiState.trip?.name.orEmpty()} payment plan:\n$lines")
    }
    context.startActivity(Intent.createChooser(intent, "Share payment plan"))
}

@ThemePreviews
@Composable
private fun OptimizedSettlementScreenPreview() {
    AppPreview {
        OptimizedSettlementScreen(
            uiState = sampleOptimizedSettlementUiState,
            onMarkPaidClick = {},
            onBackClick = {},
            onRecordSettlementClick = {},
        )
    }
}
