package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.presentation.components.label
import com.abzal.tripsplit.features.participants.presentation.components.tone

/** Circle with person initials ("MK"). */
@Composable
fun Avatar(
    initials: String,
    modifier: Modifier = Modifier,
    tone: Tone = Tone.Accent,
    size: Dp = Sizes.avatar,
) {
    val colors = tone.colors()
    Box(
        modifier = modifier.size(size).clip(CircleShape).background(colors.container),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initials,
            style = if (size < Sizes.avatar) MaterialTheme.typography.labelSmall else MaterialTheme.typography.titleSmall,
            color = colors.content,
        )
    }
}

/** Rounded square with an icon: leading element of list rows and banners. */
@Composable
fun IconBadge(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    tone: Tone = Tone.Accent,
    size: Dp = Sizes.iconBadge,
) {
    val colors = tone.colors()
    Box(
        modifier = modifier.size(size).clip(MaterialTheme.shapes.small).background(colors.container),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = colors.content, modifier = Modifier.size(Sizes.icon))
    }
}

/** Small rounded label: "Email sent", "ORGANIZER". */
@Composable
fun StatusPill(
    text: String,
    modifier: Modifier = Modifier,
    tone: Tone = Tone.Accent,
    icon: ImageVector? = null,
) {
    val colors = tone.colors()
    Surface(modifier = modifier, shape = CircleShape, color = colors.container, contentColor = colors.content) {
        Row(
            modifier = Modifier.padding(horizontal = Spacing.xs, vertical = Spacing.xxs),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.xxs),
        ) {
            if (icon != null) Icon(icon, contentDescription = null, modifier = Modifier.size(Sizes.iconTiny))
            Text(text = text, style = MaterialTheme.typography.labelMedium)
        }
    }
}

/** Tinted message box with an icon: hints, warnings, errors. */
@Composable
fun InfoBanner(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    tone: Tone = Tone.Info,
) {
    val colors = tone.colors()
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = colors.container,
        contentColor = colors.content,
    ) {
        Row(
            modifier = Modifier.padding(Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(Sizes.icon))
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

/** Big rounded square with an icon at the top of a screen (logo, "Check your inbox", "Delete this trip"). */
@Composable
fun BigIconBadge(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    containerColor: Color = AppTheme.colors.accentDark,
    contentColor: Color = AppTheme.colors.onHero,
) {
    Box(
        modifier = modifier.size(Sizes.bigBadge).clip(MaterialTheme.shapes.extraLarge).background(containerColor),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = contentColor, modifier = Modifier.size(Sizes.iconLarge))
    }
}

/** "Maya Kim" -> "MK". */
fun String.toInitials(): String =
    trim().split(" ").filter { it.isNotEmpty() }.take(2).joinToString("") { it.first().uppercase() }

@ThemePreviews
@Composable
private fun AvatarPreview() {
    AppPreview {
        Avatar(
            initials = "MK",
        )
    }
}

@ThemePreviews
@Composable
private fun IconBadgePreview() {
    AppPreview {
        IconBadge(
            icon = Icons.Outlined.Star,
        )
    }
}

@ThemePreviews
@Composable
private fun StatusPillPreview() {
    AppPreview {
        StatusPill(
            text = "Sample text",
        )
    }
}

@ThemePreviews
@Composable
private fun InfoBannerPreview() {
    AppPreview {
        InfoBanner(
            text = "Sample text",
            icon = Icons.Outlined.Star,
        )
    }
}

@ThemePreviews
@Composable
private fun BigIconBadgePreview() {
    AppPreview {
        BigIconBadge(
            icon = Icons.Outlined.Star,
        )
    }
}
