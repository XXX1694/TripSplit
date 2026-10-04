package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.border
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
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.Strokes
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.expenses.presentation.TripOverviewUiState
import com.abzal.tripsplit.features.expenses.presentation.sampleTripOverviewUiState

/** Dark card: total spent, per person, number of expenses and how much is settled. */
@Composable
fun TripTotalCard(uiState: TripOverviewUiState, modifier: Modifier = Modifier) {
    HeroCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Spacing.xxs)) {
                Text("TOTAL SPENDING", style = MaterialTheme.typography.labelSmall, color = AppTheme.colors.onHeroMuted)
                Text(formatMoney(uiState.totalSpent, uiState.currency), style = MaterialTheme.typography.displayMedium)
                Text(
                    text = "${formatMoney(uiState.perPerson, uiState.currency)} per person · ${uiState.expenses.size} expenses",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.colors.onHeroMuted,
                )
            }
            SettledBadge(percent = uiState.settledPercent)
        }
    }
}

@Composable
private fun SettledBadge(percent: Int) {
    Box(
        modifier = Modifier.size(Sizes.progressRing).border(Strokes.thin, AppTheme.colors.heroOutline, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$percent%", style = MaterialTheme.typography.titleMedium)
            Text("settled", style = MaterialTheme.typography.labelMedium, color = AppTheme.colors.onHeroMuted)
        }
    }
}

@ThemePreviews
@Composable
private fun TripTotalCardPreview() {
    AppPreview {
        TripTotalCard(
            uiState = sampleTripOverviewUiState,
        )
    }
}
