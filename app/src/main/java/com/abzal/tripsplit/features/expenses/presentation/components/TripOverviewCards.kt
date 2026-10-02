package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.AppSegmentedBar
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.BarSegment
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.core.util.formatSignedMoney
import com.abzal.tripsplit.features.expenses.presentation.TripOverviewUiState
import kotlin.math.abs

private val mutedWhite = Color.White.copy(alpha = 0.8f)

/** Dark card: total spent, per person, number of expenses and how much is settled. */
@Composable
fun TripTotalCard(uiState: TripOverviewUiState, modifier: Modifier = Modifier) {
    HeroCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Spacing.xxs)) {
                Text("TOTAL SPENDING", style = MaterialTheme.typography.labelSmall, color = mutedWhite)
                Text(formatMoney(uiState.totalSpent, uiState.currency), style = MaterialTheme.typography.displayMedium)
                Text(
                    text = "${formatMoney(uiState.perPerson, uiState.currency)} per person · ${uiState.expenses.size} expenses",
                    style = MaterialTheme.typography.bodySmall,
                    color = mutedWhite,
                )
            }
            SettledBadge(percent = uiState.settledPercent)
        }
    }
}

@Composable
private fun SettledBadge(percent: Int) {
    Box(
        modifier = Modifier.size(64.dp).border(1.dp, mutedWhite.copy(alpha = 0.4f), CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$percent%", style = MaterialTheme.typography.titleMedium)
            Text("settled", style = MaterialTheme.typography.labelMedium, color = mutedWhite)
        }
    }
}

/** Top categories with amounts and a colored bar. */
@Composable
fun CategorySpendingCard(
    uiState: TripOverviewUiState,
    onInsightsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val top = uiState.categories.take(4)
    AppCard(modifier = modifier) {
        SectionHeader(title = "Spending by category", actionText = "Insights", onActionClick = onInsightsClick)
        if (top.isEmpty()) {
            EmptyHint("No expenses yet")
        } else {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                top.forEach { CategoryLegend(it.category, formatMoney(it.total, uiState.currency), categoryTone(it.category).colors().content) }
            }
            AppSegmentedBar(top.map { BarSegment(it.total.toFloat(), categoryTone(it.category).colors().content) })
        }
    }
}

@Composable
private fun CategoryLegend(name: String, amount: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(color))
        Text(amount, style = MaterialTheme.typography.titleSmall, color = AppTheme.colors.textPrimary)
        Text(name, style = MaterialTheme.typography.bodySmall, color = AppTheme.colors.textSecondary)
    }
}

/** Biggest balances of the trip. */
@Composable
fun BalancesPreviewCard(
    uiState: TripOverviewUiState,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val top = uiState.balances.sortedByDescending { abs(it.amount) }.take(3)
    AppCard(modifier = modifier) {
        SectionHeader(title = "Balances", actionText = "See all", onActionClick = onSeeAllClick)
        if (top.isEmpty()) EmptyHint("Add participants to see balances")
        top.forEach { balance ->
            val index = uiState.participants.indexOfFirst { it.id == balance.participantId }
            val name = uiState.participants.getOrNull(index)?.name.orEmpty()
            val isOwed = balance.amount >= 0
            AppListRow(
                title = if (isOwed) "$name gets back" else "$name owes",
                leading = { Avatar(initials = name.toInitials(), tone = avatarToneAt(index)) },
                trailing = {
                    Text(
                        text = formatSignedMoney(balance.amount, uiState.currency),
                        style = MaterialTheme.typography.titleSmall,
                        color = if (isOwed) AppTheme.colors.positive else AppTheme.colors.negative,
                    )
                },
            )
        }
    }
}

@Composable
fun EmptyHint(text: String, modifier: Modifier = Modifier) {
    Text(text, modifier = modifier, style = MaterialTheme.typography.bodyMedium, color = AppTheme.colors.textSecondary)
}
