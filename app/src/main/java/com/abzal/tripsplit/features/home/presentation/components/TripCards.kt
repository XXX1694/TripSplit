package com.abzal.tripsplit.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.abzal.tripsplit.core.designsystem.components.AvatarStack
import com.abzal.tripsplit.core.designsystem.components.CoverImage
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.core.util.formatTripDates
import com.abzal.tripsplit.features.home.presentation.TripSummary
import com.abzal.tripsplit.features.trips.domain.model.Trip

/** Big card with the cover on top. */
@Composable
fun ActiveTripCard(
    summary: TripSummary,
    onClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(modifier = modifier, onClick = onClick, contentPadding = 0.dp, verticalArrangement = Arrangement.Top) {
        CoverImage(modifier = Modifier.fillMaxWidth().height(82.dp))
        Column(modifier = Modifier.padding(Spacing.md), verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            TripTitleRow(summary, onMoreClick)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                AvatarStack(names = summary.participantNames)
                TripTotal(summary, amountColor = AppTheme.colors.primary)
            }
        }
    }
}

/** Compact card with the cover on the left. */
@Composable
fun PastTripCard(
    summary: TripSummary,
    onClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(modifier = modifier, onClick = onClick, contentPadding = 0.dp, verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            CoverImage(modifier = Modifier.width(104.dp).fillMaxHeight())
            Column(
                modifier = Modifier.weight(1f).padding(Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                TripTitleRow(summary, onMoreClick)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    AvatarStack(names = summary.participantNames)
                    TripTotal(summary, amountColor = AppTheme.colors.textPrimary)
                }
            }
        }
    }
}

@Composable
private fun TripTitleRow(summary: TripSummary, onMoreClick: () -> Unit) {
    Row(verticalAlignment = Alignment.Top) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = summary.trip.name,
                style = MaterialTheme.typography.titleMedium,
                color = AppTheme.colors.textPrimary,
            )
            Text(
                text = tripSubtitle(summary.trip),
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.colors.textSecondary,
            )
        }
        IconButton(onClick = onMoreClick) {
            Icon(Icons.Outlined.MoreVert, contentDescription = "Trip options")
        }
    }
}

@Composable
private fun TripTotal(summary: TripSummary, amountColor: Color) {
    Column(horizontalAlignment = Alignment.End) {
        Text(
            text = "Total spent",
            style = MaterialTheme.typography.bodySmall,
            color = AppTheme.colors.textSecondary,
        )
        Text(
            text = formatMoney(summary.totalSpent, summary.trip.currency),
            style = MaterialTheme.typography.titleMedium,
            color = amountColor,
        )
    }
}

private fun tripSubtitle(trip: Trip): String {
    val dates = formatTripDates(trip.startDateMillis, trip.endDateMillis)
    return listOf(trip.destination, dates, trip.currency).filter { it.isNotBlank() }.joinToString(" · ")
}
