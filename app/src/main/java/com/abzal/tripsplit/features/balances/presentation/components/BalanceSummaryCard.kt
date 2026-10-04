package com.abzal.tripsplit.features.balances.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.balances.presentation.BalancesUiState
import com.abzal.tripsplit.features.balances.presentation.sampleBalancesUiState
import com.abzal.tripsplit.features.participants.presentation.components.label

/** Dark card: how much is still to settle, with trip total, per person and already paid back. */
@Composable
fun BalanceSummaryCard(uiState: BalancesUiState, modifier: Modifier = Modifier) {
    HeroCard(modifier = modifier) {
        Text("STILL TO SETTLE", style = MaterialTheme.typography.labelSmall, color = AppTheme.colors.onHeroMuted)
        Text(formatMoney(uiState.unsettledTotal, uiState.currency), style = MaterialTheme.typography.displayMedium)
        HorizontalDivider(color = AppTheme.colors.heroOutline)
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
        Text(label, style = MaterialTheme.typography.labelSmall, color = AppTheme.colors.onHeroMuted)
        Text(value, style = MaterialTheme.typography.titleSmall)
    }
}

@ThemePreviews
@Composable
private fun BalanceSummaryCardPreview() {
    AppPreview {
        BalanceSummaryCard(
            uiState = sampleBalancesUiState,
        )
    }
}
