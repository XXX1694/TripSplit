package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.presentation.components.tone

private val avatarTones = listOf(Tone.Accent, Tone.Warning, Tone.Info, Tone.Danger)

/** Tone for the participant at [index], so the same person always gets the same color. */
fun avatarToneAt(index: Int): Tone = avatarTones[index % avatarTones.size]

/** Overlapping avatars of participants: "MK LE SA +1". */
@Composable
fun AvatarStack(
    names: List<String>,
    modifier: Modifier = Modifier,
    maxVisible: Int = 3,
) {
    val visible = names.take(maxVisible)
    val hiddenCount = names.size - visible.size

    Row(modifier = modifier) {
        visible.forEachIndexed { index, name ->
            Avatar(
                initials = name.toInitials(),
                tone = avatarToneAt(index),
                size = Sizes.avatarSmall,
                modifier = Modifier.overlap(index),
            )
        }
        if (hiddenCount > 0) {
            Avatar(
                initials = "+$hiddenCount",
                tone = Tone.Accent,
                size = Sizes.avatarSmall,
                modifier = Modifier.overlap(visible.size),
            )
        }
    }
}

private fun Modifier.overlap(index: Int): Modifier =
    if (index == 0) this else offset(x = (-6 * index).dp)

/** Placeholder for a trip photo until real covers are added. */
@Composable
fun CoverImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(AppTheme.colors.accentSoft),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.Landscape,
            contentDescription = null,
            tint = AppTheme.colors.accent,
            modifier = Modifier.size(Sizes.iconLarge),
        )
    }
}

@ThemePreviews
@Composable
private fun AvatarStackPreview() {
    AppPreview {
        AvatarStack(
            names = listOf("Food", "Stay", "Transit"),
        )
    }
}

@ThemePreviews
@Composable
private fun CoverImagePreview() {
    AppPreview {
        CoverImage()
    }
}
