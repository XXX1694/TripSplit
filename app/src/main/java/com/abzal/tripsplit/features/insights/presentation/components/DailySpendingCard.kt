package com.abzal.tripsplit.features.insights.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.AppBarChart
import com.abzal.tripsplit.core.designsystem.components.BarValue
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.core.util.formatTripDates
import com.abzal.tripsplit.features.insights.presentation.SpendingInsightsUiState
import com.abzal.tripsplit.features.insights.presentation.sampleSpendingInsightsUiState
import com.abzal.tripsplit.features.participants.presentation.components.label

/** Dark card: daily average, the most expensive day and bars of the last days. */
@Composable
fun DailySpendingCard(uiState: SpendingInsightsUiState, modifier: Modifier = Modifier) {
    val days = uiState.days.takeLast(7)
    val peak = uiState.peakDay

    HeroCard(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("DAILY AVERAGE", style = MaterialTheme.typography.labelSmall, color = AppTheme.colors.onHeroMuted)
                Text(formatMoney(uiState.dailyAverage, uiState.currency), style = MaterialTheme.typography.headlineMedium)
            }
            if (peak != null) {
                Column(horizontalAlignment = Alignment.End) {
                    Text("PEAK DAY", style = MaterialTheme.typography.labelSmall, color = AppTheme.colors.onHeroMuted)
                    Text(
                        text = "${formatTripDates(peak.dayStartMillis, null)} · ${formatMoney(peak.total, uiState.currency)}",
                        style = MaterialTheme.typography.titleSmall,
                        color = AppTheme.colors.warningContainer,
                    )
                }
            }
        }
        if (days.isEmpty()) {
            Text("No expenses yet", style = MaterialTheme.typography.bodyMedium, color = AppTheme.colors.onHeroMuted)
        } else {
            AppBarChart(
                values = days.map {
                    BarValue(
                        label = formatTripDates(it.dayStartMillis, null).substringBefore(' '),
                        value = it.total,
                        isHighlighted = it == peak,
                    )
                },
                barColor = AppTheme.colors.heroBar,
                highlightColor = AppTheme.colors.warning,
                labelColor = AppTheme.colors.onHeroMuted,
            )
        }
    }
}

@ThemePreviews
@Composable
private fun DailySpendingCardPreview() {
    AppPreview {
        DailySpendingCard(
            uiState = sampleSpendingInsightsUiState,
        )
    }
}
