package com.abzal.tripsplit.features.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.captionMedium
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.home.presentation.TripSummary
import com.abzal.tripsplit.features.home.presentation.sampleTripSummary

@Composable
fun TripTotal(summary: TripSummary, amountColor: Color) {
    Column(horizontalAlignment = Alignment.End) {
        Text(
            text = "Total spent",
            style = MaterialTheme.typography.captionMedium,
            color = AppTheme.colors.textMuted,
        )
        Text(
            text = formatMoney(summary.totalSpent, summary.trip.currency),
            style = MaterialTheme.typography.titleMedium,
            color = amountColor,
        )
    }
}

@ThemePreviews
@Composable
private fun TripTotalPreview() {
    AppPreview {
        TripTotal(
            summary = sampleTripSummary,
            amountColor = AppTheme.colors.accent,
        )
    }
}
