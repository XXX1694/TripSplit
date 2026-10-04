package com.abzal.tripsplit.features.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatTripDates
import com.abzal.tripsplit.features.home.presentation.TripSummary
import com.abzal.tripsplit.features.home.presentation.sampleTripSummary
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun TripTitleRow(summary: TripSummary, onMoreClick: () -> Unit) {
    Row(verticalAlignment = Alignment.Top) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = summary.trip.name,
                style = MaterialTheme.typography.titleMedium,
                color = AppTheme.colors.text,
            )
            Text(
                text = tripSubtitle(summary.trip),
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.colors.textMuted,
            )
        }
        IconButton(onClick = onMoreClick) {
            Icon(Icons.Outlined.MoreVert, contentDescription = "Trip options")
        }
    }
}

private fun tripSubtitle(trip: Trip): String {
    val dates = formatTripDates(trip.startDateMillis, trip.endDateMillis)
    return listOf(trip.destination, dates, trip.currency).filter { it.isNotBlank() }.joinToString(" · ")
}

@ThemePreviews
@Composable
private fun TripTitleRowPreview() {
    AppPreview {
        TripTitleRow(
            summary = sampleTripSummary,
            onMoreClick = {},
        )
    }
}
