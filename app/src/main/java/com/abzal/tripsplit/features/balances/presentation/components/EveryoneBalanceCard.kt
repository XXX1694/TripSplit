package com.abzal.tripsplit.features.balances.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.AppProgressBar
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.core.util.formatSignedMoney
import com.abzal.tripsplit.features.balances.presentation.BalancesUiState
import com.abzal.tripsplit.features.balances.presentation.sampleBalancesUiState
import com.abzal.tripsplit.features.participants.presentation.components.tone
import kotlin.math.abs

/** Everyone's paid amount, share and net balance with a bar. */
@Composable
fun EveryoneBalanceCard(uiState: BalancesUiState, modifier: Modifier = Modifier) {
    val maxAmount = uiState.balances.maxOfOrNull { abs(it.amount) }?.takeIf { it > 0 } ?: 1.0

    AppCard(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
        Text("Everyone's balance", style = MaterialTheme.typography.titleMedium, color = AppTheme.colors.text)
        uiState.balances.forEachIndexed { index, balance ->
            val name = uiState.participants.firstOrNull { it.id == balance.participantId }?.name.orEmpty()
            val isOwed = balance.amount >= 0
            val color = if (isOwed) AppTheme.colors.positive else AppTheme.colors.danger

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                AppListRow(
                    title = name,
                    subtitle = "Paid ${formatMoney(uiState.paidBy(balance.participantId), uiState.currency)} · " +
                        "Share ${formatMoney(uiState.shareOf(balance.participantId), uiState.currency)}",
                    leading = { Avatar(initials = name.toInitials(), tone = avatarToneAt(index), size = Sizes.avatarMedium) },
                    trailing = {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = if (isOwed) "GETS BACK" else "OWES",
                                style = MaterialTheme.typography.labelSmall,
                                color = AppTheme.colors.textMuted,
                            )
                            Text(
                                text = formatSignedMoney(balance.amount, uiState.currency),
                                style = MaterialTheme.typography.titleSmall,
                                color = color,
                            )
                        }
                    },
                )
                AppProgressBar(progress = (abs(balance.amount) / maxAmount).toFloat(), color = color)
            }
        }
    }
}

@ThemePreviews
@Composable
private fun EveryoneBalanceCardPreview() {
    AppPreview {
        EveryoneBalanceCard(
            uiState = sampleBalancesUiState,
        )
    }
}
