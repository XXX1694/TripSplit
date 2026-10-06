package com.abzal.tripsplit.features.home.presentation.components

import com.abzal.tripsplit.core.designsystem.components.coverImageRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AvatarStack
import com.abzal.tripsplit.core.designsystem.components.CoverImage
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.home.presentation.TripSummary
import com.abzal.tripsplit.features.home.presentation.sampleTripSummary

/** Compact card with the cover on the left. */
@Composable
fun PastTripCard(
    summary: TripSummary,
    onClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(modifier = modifier, onClick = onClick, contentPadding = Spacing.none, verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            CoverImage(
                contentDescription = "Cover photo of ${summary.trip.name}",
                imageRes = coverImageRes(summary.trip.cover),
                modifier = Modifier.width(Sizes.coverSide).fillMaxHeight(),
            )
            Column(
                modifier = Modifier.weight(1f).padding(Spacing.space16),
                verticalArrangement = Arrangement.spacedBy(Spacing.space12),
            ) {
                TripTitleRow(summary, onMoreClick)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    AvatarStack(names = summary.participantNames)
                    TripTotal(summary, amountColor = AppTheme.colors.text)
                }
            }
        }
    }
}

@ThemePreviews
@Composable
private fun PastTripCardPreview() {
    AppPreview {
        PastTripCard(
            summary = sampleTripSummary,
            onClick = {},
            onMoreClick = {},
        )
    }
}
