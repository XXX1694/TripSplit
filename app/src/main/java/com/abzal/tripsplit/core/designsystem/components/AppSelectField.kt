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
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.Strokes
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.presentation.components.label

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
        border = BorderStroke(Strokes.thin, colors.border),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Spacing.space16, vertical = Spacing.space8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.space12),
        ) {
            if (leadingContent != null) {
                leadingContent()
            } else if (leadingIcon != null) {
                Icon(leadingIcon, contentDescription = null, tint = colors.textMuted)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = label, style = MaterialTheme.typography.bodySmall, color = colors.textMuted)
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (isPlaceholder) colors.textDisabled else colors.text,
                )
                if (caption != null) {
                    Text(text = caption, style = MaterialTheme.typography.bodySmall, color = colors.textMuted)
                }
            }
            trailing?.invoke()
        }
    }
}

@ThemePreviews
@Composable
private fun AppSelectFieldPreview() {
    AppPreview {
        AppSelectField(
            label = "Label",
            value = "Value",
            onClick = {},
        )
    }
}
