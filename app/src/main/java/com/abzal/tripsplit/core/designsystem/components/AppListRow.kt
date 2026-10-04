package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews

/**
 * Universal row: [leading] (avatar / icon badge), title with subtitle, [trailing] (amount, switch, arrow).
 * Used for settings, participants, expenses, balances.
 */
@Composable
fun AppListRow(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .let { if (onClick != null) it.clickable(onClick = onClick) else it }
            .heightIn(min = Sizes.touchTarget)
            .padding(vertical = Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        leading?.invoke()
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleSmall, color = AppTheme.colors.textPrimary)
            if (subtitle != null) {
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = AppTheme.colors.textSecondary)
            }
        }
        trailing?.invoke()
    }
}

@Composable
fun AppDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier = modifier, color = AppTheme.colors.divider)
}

/** Arrow at the end of a clickable row. */
@Composable
fun ChevronIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
        contentDescription = null,
        modifier = modifier,
        tint = AppTheme.colors.textSecondary,
    )
}

/** Short bold value at the end of a row: "EUR €", "On", "Healthy". */
@Composable
fun RowValue(text: String, modifier: Modifier = Modifier, color: Color = AppTheme.colors.primary) {
    Text(text = text, modifier = modifier, style = MaterialTheme.typography.labelLarge, color = color)
}

@ThemePreviews
@Composable
private fun AppDividerPreview() {
    AppPreview {
        AppDivider()
    }
}

@ThemePreviews
@Composable
private fun AppListRowPreview() {
    AppPreview {
        AppListRow(
            title = "Title",
        )
    }
}

@ThemePreviews
@Composable
private fun ChevronIconPreview() {
    AppPreview {
        ChevronIcon()
    }
}

@ThemePreviews
@Composable
private fun RowValuePreview() {
    AppPreview {
        RowValue(
            text = "Sample text",
        )
    }
}
