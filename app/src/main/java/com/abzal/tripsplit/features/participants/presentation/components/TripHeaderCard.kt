package com.abzal.tripsplit.features.participants.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.captionMedium
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.designsystem.titleRegular
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.preview.sampleTrip
import com.abzal.tripsplit.core.util.formatTripDates
import com.abzal.tripsplit.features.trips.domain.model.Trip

/** Dark card at the top: which trip the participants are added to. */
@Composable
fun TripHeaderCard(
    trip: Trip?,
    caption: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.PersonAdd,
) {
    HeroCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.space16)) {
            Box(
                modifier = Modifier.size(Sizes.iconBadgeLarge).clip(MaterialTheme.shapes.medium).background(AppTheme.colors.heroOverlay),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, contentDescription = null)
            }
            Column {
                Text(trip?.name.orEmpty(), style = MaterialTheme.typography.titleRegular)
                Text(
                    text = listOfNotNull(trip?.let { formatTripDates(it.startDateMillis, it.endDateMillis) }, caption).joinToString(" · "),
                    style = MaterialTheme.typography.captionMedium,
                    color = AppTheme.colors.onHeroMuted,
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun TripHeaderCardPreview() {
    AppPreview {
        TripHeaderCard(
            trip = sampleTrip,
            caption = "Caption",
        )
    }
}
