package com.abzal.tripsplit.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

/** Big card with the cover on top. */
@Composable
fun ActiveTripCard(
    summary: TripSummary,
    onClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(modifier = modifier, onClick = onClick, contentPadding = Spacing.none, verticalArrangement = Arrangement.Top) {
        CoverImage(modifier = Modifier.fillMaxWidth().height(Sizes.coverCard))
        Column(modifier = Modifier.padding(Spacing.md), verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            TripTitleRow(summary, onMoreClick)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                AvatarStack(names = summary.participantNames)
                TripTotal(summary, amountColor = AppTheme.colors.accent)
            }
        }
    }
}

@ThemePreviews
@Composable
private fun ActiveTripCardPreview() {
    AppPreview {
        ActiveTripCard(
            summary = sampleTripSummary,
            onClick = {},
            onMoreClick = {},
        )
    }
}
