package com.abzal.tripsplit.features.expenses.presentation.components

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
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppSegmentedBar
import com.abzal.tripsplit.core.designsystem.components.BarSegment
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.expenses.presentation.TripOverviewUiState
import com.abzal.tripsplit.features.expenses.presentation.sampleTripOverviewUiState

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
        Box(Modifier.size(Sizes.dot).clip(CircleShape).background(color))
        Text(amount, style = MaterialTheme.typography.titleSmall, color = AppTheme.colors.textPrimary)
        Text(name, style = MaterialTheme.typography.bodySmall, color = AppTheme.colors.textSecondary)
    }
}

@ThemePreviews
@Composable
private fun CategorySpendingCardPreview() {
    AppPreview {
        CategorySpendingCard(
            uiState = sampleTripOverviewUiState,
            onInsightsClick = {},
        )
    }
}
