package com.abzal.tripsplit.features.insights.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.captionMedium
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppDonutChart
import com.abzal.tripsplit.core.designsystem.components.BarSegment
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.expenses.presentation.components.categoryTone
import com.abzal.tripsplit.features.insights.presentation.SpendingInsightsUiState
import com.abzal.tripsplit.features.insights.presentation.sampleSpendingInsightsUiState

/** Donut with the total and a legend: color, category, percent and amount. */
@Composable
fun CategoryBreakdownCard(uiState: SpendingInsightsUiState, modifier: Modifier = Modifier) {
    val total = uiState.spending.sumOf { it.total }

    AppCard(modifier = modifier) {
        SectionHeader(title = "Category breakdown")
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.space16)) {
            AppDonutChart(
                segments = uiState.spending.map { BarSegment(it.total.toFloat(), categoryTone(it.category).colors().content) },
                center = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(formatMoney(total, uiState.currency), style = MaterialTheme.typography.titleSmall)
                        Text("TOTAL", style = MaterialTheme.typography.labelSmall, color = AppTheme.colors.textMuted)
                    }
                },
            )
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Spacing.space8)) {
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
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.space8)) {
        Box(Modifier.size(Sizes.dot).clip(CircleShape).background(color))
        Text(name, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium, color = AppTheme.colors.text)
        Text("%.1f%%".format(percent), style = MaterialTheme.typography.captionMedium, color = AppTheme.colors.textMuted)
        Text(amount, style = MaterialTheme.typography.titleSmall, color = AppTheme.colors.text, textAlign = TextAlign.End)
    }
}

@ThemePreviews
@Composable
private fun CategoryBreakdownCardPreview() {
    AppPreview {
        CategoryBreakdownCard(
            uiState = sampleSpendingInsightsUiState,
        )
    }
}
