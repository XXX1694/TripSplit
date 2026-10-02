package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing

/**
 * Field that looks like [AppTextField] but opens something on click (date picker, currency list, dropdown).
 * Shows a small [label], the [value] and an optional [caption]. [leadingContent] replaces [leadingIcon] (e.g. an avatar).
 */
@Composable
fun AppSelectField(
    label: String,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    caption: String? = null,
    isPlaceholder: Boolean = false,
    leadingContent: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    val colors = AppTheme.colors
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().heightIn(min = Sizes.field),
        shape = MaterialTheme.shapes.large,
        color = colors.surface,
        border = BorderStroke(1.dp, colors.outline),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Spacing.md, vertical = Spacing.xs),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        ) {
            if (leadingContent != null) {
                leadingContent()
            } else if (leadingIcon != null) {
                Icon(leadingIcon, contentDescription = null, tint = colors.textSecondary)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = label, style = MaterialTheme.typography.bodySmall, color = colors.textSecondary)
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (isPlaceholder) colors.textDisabled else colors.textPrimary,
                )
                if (caption != null) {
                    Text(text = caption, style = MaterialTheme.typography.bodySmall, color = colors.textSecondary)
                }
            }
            trailing?.invoke()
        }
    }
}
