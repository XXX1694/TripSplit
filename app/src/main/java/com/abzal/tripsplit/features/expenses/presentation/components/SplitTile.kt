package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.Strokes
import com.abzal.tripsplit.core.designsystem.captionMedium
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.preview.sampleParticipants
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.participants.presentation.components.tone

@Composable
fun SplitTile(
    participant: Participant,
    toneIndex: Int,
    isSelected: Boolean,
    percent: Int?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) colors.accentSoft else colors.surface,
        border = BorderStroke(Strokes.thin, if (isSelected) colors.accent else colors.border),
    ) {
        Row(
            modifier = Modifier.padding(Spacing.space12),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.space8),
        ) {
            Avatar(initials = participant.name.toInitials(), tone = avatarToneAt(toneIndex), size = Sizes.avatarSmall)
            Text(
                text = participant.name.substringBefore(' '),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleSmall,
                color = colors.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (percent != null) {
                Text("$percent%", style = MaterialTheme.typography.captionMedium, color = colors.textMuted)
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Included",
                    tint = colors.onAccent,
                    modifier = Modifier.size(Sizes.icon).background(colors.accent, MaterialTheme.shapes.extraSmall),
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun SplitTilePreview() {
    AppPreview {
        SplitTile(
            participant = sampleParticipants.first(),
            toneIndex = 0,
            isSelected = true,
            percent = 25,
            onClick = {},
        )
    }
}
