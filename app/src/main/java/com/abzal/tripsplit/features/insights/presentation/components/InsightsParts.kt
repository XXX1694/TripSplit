package com.abzal.tripsplit.features.insights.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppBarChart
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppDonutChart
import com.abzal.tripsplit.core.designsystem.components.BarSegment
import com.abzal.tripsplit.core.designsystem.components.BarValue
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.core.util.formatTripDates
import com.abzal.tripsplit.features.expenses.presentation.components.categoryTone
import com.abzal.tripsplit.features.insights.presentation.SpendingInsightsUiState

private val mutedWhite = Color.White.copy(alpha = 0.8f)

/** Dark card: daily average, the most expensive day and bars of the last days. */
@Composable
fun DailySpendingCard(uiState: SpendingInsightsUiState, modifier: Modifier = Modifier) {
    val days = uiState.days.takeLast(7)
    val peak = uiState.peakDay

    HeroCard(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("DAILY AVERAGE", style = MaterialTheme.typography.labelSmall, color = mutedWhite)
                Text(formatMoney(uiState.dailyAverage, uiState.currency), style = MaterialTheme.typography.headlineMedium)
            }
            if (peak != null) {
                Column(horizontalAlignment = Alignment.End) {
                    Text("PEAK DAY", style = MaterialTheme.typography.labelSmall, color = mutedWhite)
                    Text(
                        text = "${formatTripDates(peak.dayStartMillis, null)} · ${formatMoney(peak.total, uiState.currency)}",
                        style = MaterialTheme.typography.titleSmall,
                        color = AppTheme.colors.warningContainer,
                    )
                }
            }
        }
        if (days.isEmpty()) {
            Text("No expenses yet", style = MaterialTheme.typography.bodyMedium, color = mutedWhite)
        } else {
            AppBarChart(
                values = days.map {
                    BarValue(
                        label = formatTripDates(it.dayStartMillis, null).substringBefore(' '),
                        value = it.total,
                        isHighlighted = it == peak,
                    )
                },
                barColor = Color.White.copy(alpha = 0.35f),
                highlightColor = AppTheme.colors.warning,
                labelColor = mutedWhite,
            )
        }
    }
}

/** Donut with the total and a legend: color, category, percent and amount. */
@Composable
fun CategoryBreakdownCard(uiState: SpendingInsightsUiState, modifier: Modifier = Modifier) {
    val total = uiState.spending.sumOf { it.total }

    AppCard(modifier = modifier) {
        SectionHeader(title = "Category breakdown")
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
            AppDonutChart(
                segments = uiState.spending.map { BarSegment(it.total.toFloat(), categoryTone(it.category).colors().content) },
                center = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(formatMoney(total, uiState.currency), style = MaterialTheme.typography.titleSmall)
                        Text("TOTAL", style = MaterialTheme.typography.labelSmall, color = AppTheme.colors.textSecondary)
                    }
                },
            )
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                uiState.spending.forEach { item ->
                    LegendRow(
                        color = categoryTone(item.category).colors().content,
                        name = item.category,
                        percent = if (total > 0) item.total / total * 100 else 0.0,
                        amount = formatMoney(item.total, uiState.currency),
                    )
                }
            }
        }
    }
}

@Composable
private fun LegendRow(color: Color, name: String, percent: Double, amount: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(color))
        Text(name, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium, color = AppTheme.colors.textPrimary)
        Text("%.1f%%".format(percent), style = MaterialTheme.typography.bodySmall, color = AppTheme.colors.textSecondary)
        Text(amount, style = MaterialTheme.typography.titleSmall, color = AppTheme.colors.textPrimary, textAlign = TextAlign.End)
    }
}

/** Totals per currency, shown only when expenses use more than one currency. */
@Composable
fun CurrencyTotalsCard(uiState: SpendingInsightsUiState, modifier: Modifier = Modifier) {
    AppCard(modifier = modifier) {
        SectionHeader(title = "Spending by currency")
        uiState.currencyTotals.forEach { (code, total) ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(code, style = MaterialTheme.typography.bodyMedium, color = AppTheme.colors.textPrimary)
                Text(formatMoney(total, code), style = MaterialTheme.typography.titleSmall, color = AppTheme.colors.textPrimary)
            }
        }
    }
}
