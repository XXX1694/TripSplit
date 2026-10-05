package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.Strokes
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews

/** Pill-shaped selectable chip: categories, trip type. */
@Composable
fun AppChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    val colors = AppTheme.colors
    Surface(
        onClick = onClick,
        modifier = modifier.heightIn(min = Sizes.touchTarget),
        shape = CircleShape,
        color = if (selected) colors.accent else colors.surface,
        contentColor = if (selected) colors.onAccent else colors.textMuted,
        border = if (selected) null else BorderStroke(Strokes.thin, colors.border),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Spacing.space16, vertical = Spacing.space8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.space8),
        ) {
            if (icon != null) Icon(icon, contentDescription = null, modifier = Modifier.size(Sizes.iconSmall))
            Text(text = text, style = MaterialTheme.typography.labelMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun AppSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        colors = SwitchDefaults.colors(
            checkedThumbColor = colors.onAccent,
            checkedTrackColor = colors.accent,
            uncheckedThumbColor = colors.surface,
            uncheckedTrackColor = colors.border,
            uncheckedBorderColor = colors.border,
        ),
    )
}

/** Checkbox with a text next to it: "Remember me", "I agree to the Terms". The whole row is tappable. */
@Composable
fun AppCheckboxRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .heightIn(min = Sizes.touchTarget)
            .toggleable(value = checked, role = Role.Checkbox, onValueChange = onCheckedChange),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = AppTheme.colors.accent,
                uncheckedColor = AppTheme.colors.border,
                checkmarkColor = AppTheme.colors.onAccent,
            ),
            modifier = Modifier.padding(horizontal = Spacing.space12),
        )
        Text(text = text, style = MaterialTheme.typography.bodyMedium, color = AppTheme.colors.textMuted)
    }
}

@ThemePreviews
@Composable
private fun AppSwitchPreview() {
    AppPreview {
        AppSwitch(
            checked = true,
            onCheckedChange = {},
        )
    }
}

@ThemePreviews
@Composable
private fun AppChipPreview() {
    AppPreview {
        AppChip(
            text = "Sample text",
            selected = true,
            onClick = {},
        )
    }
}

@ThemePreviews
@Composable
private fun AppCheckboxRowPreview() {
    AppPreview {
        AppCheckboxRow(
            text = "Sample text",
            checked = true,
            onCheckedChange = {},
        )
    }
}
