package com.abzal.tripsplit.features.balances.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.IconBadge
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.balances.presentation.OptimizedSettlementUiState
import com.abzal.tripsplit.features.balances.presentation.sampleOptimizedSettlementUiState
import com.abzal.tripsplit.features.participants.presentation.components.tone

@Composable
fun PlanSummaryCard(uiState: OptimizedSettlementUiState) {
    HeroCard {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
            IconBadge(icon = Icons.Outlined.Tune, tone = Tone.Accent)
            Column {
                Text("${uiState.transfers.size} payments settle everyone", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Optimized to the minimum number of transfers.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.colors.onHeroMuted,
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun PlanSummaryCardPreview() {
    AppPreview {
        PlanSummaryCard(
            uiState = sampleOptimizedSettlementUiState,
        )
    }
}
