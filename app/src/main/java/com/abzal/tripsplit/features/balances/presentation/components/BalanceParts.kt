package com.abzal.tripsplit.features.balances.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.AppProgressBar
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.core.util.formatSignedMoney
import com.abzal.tripsplit.features.balances.presentation.BalancesUiState
import kotlin.math.abs

private val mutedWhite = Color.White.copy(alpha = 0.8f)

/** Dark card: how much is still to settle, with trip total, per person and already paid back. */
@Composable
fun BalanceSummaryCard(uiState: BalancesUiState, modifier: Modifier = Modifier) {
    HeroCard(modifier = modifier) {
        Text("STILL TO SETTLE", style = MaterialTheme.typography.labelSmall, color = mutedWhite)
        Text(formatMoney(uiState.unsettledTotal, uiState.currency), style = MaterialTheme.typography.displayMedium)
        HorizontalDivider(color = Color.White.copy(alpha = 0.3f))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            SummaryStat("TRIP TOTAL", formatMoney(uiState.totalSpent, uiState.currency))
            SummaryStat("PER PERSON", formatMoney(uiState.perPerson, uiState.currency))
            SummaryStat("PAID BACK", formatMoney(uiState.settledTotal, uiState.currency))
        }
    }
}

@Composable
private fun SummaryStat(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall, color = mutedWhite)
        Text(value, style = MaterialTheme.typography.titleSmall)
    }
}

/** Everyone's paid amount, share and net balance with a bar. */
@Composable
fun EveryoneBalanceCard(uiState: BalancesUiState, modifier: Modifier = Modifier) {
    val maxAmount = uiState.balances.maxOfOrNull { abs(it.amount) }?.takeIf { it > 0 } ?: 1.0

    AppCard(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
        Text("Everyone's balance", style = MaterialTheme.typography.titleMedium, color = AppTheme.colors.textPrimary)
        uiState.balances.forEachIndexed { index, balance ->
            val name = uiState.participants.firstOrNull { it.id == balance.participantId }?.name.orEmpty()
            val isOwed = balance.amount >= 0
            val color = if (isOwed) AppTheme.colors.positive else AppTheme.colors.negative

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                AppListRow(
                    title = name,
                    subtitle = "Paid ${formatMoney(uiState.paidBy(balance.participantId), uiState.currency)} · " +
                        "Share ${formatMoney(uiState.shareOf(balance.participantId), uiState.currency)}",
                    leading = { Avatar(initials = name.toInitials(), tone = avatarToneAt(index), size = 48.dp) },
                    trailing = {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = if (isOwed) "GETS BACK" else "OWES",
                                style = MaterialTheme.typography.labelSmall,
                                color = AppTheme.colors.textSecondary,
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
