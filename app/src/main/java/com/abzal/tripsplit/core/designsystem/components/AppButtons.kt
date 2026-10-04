package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.Strokes
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews

/** Filled green button: the main action of a screen. */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    val colors = AppTheme.colors
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(Sizes.button),
        enabled = enabled && !isLoading,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.accent,
            contentColor = colors.onAccent,
            disabledContainerColor = colors.accent,
            disabledContentColor = colors.onAccent,
        ),
    ) {
        ButtonContent(text, icon, isLoading)
    }
}

/** White button with border: secondary action ("Create an account"). */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val colors = AppTheme.colors
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(Sizes.button),
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(Strokes.thin, colors.border),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = colors.surface, contentColor = colors.accent),
    ) {
        ButtonContent(text, icon, isLoading = false)
    }
}

/** Red tinted button for destructive actions ("Delete trip"). */
@Composable
fun DangerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val colors = AppTheme.colors
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(Sizes.button),
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.dangerContainer,
            contentColor = colors.danger,
            disabledContainerColor = colors.dangerContainer,
            disabledContentColor = colors.danger,
        ),
    ) {
        ButtonContent(text, icon, isLoading = false)
    }
}

/** Text-only green action: "Save", "Forgot password?", "Back to sign in". */
@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.heightIn(min = Sizes.touchTarget),
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(contentColor = AppTheme.colors.accent),
    ) {
        ButtonContent(text, icon, isLoading = false)
    }
}

@Composable
private fun RowScope.ButtonContent(text: String, icon: ImageVector?, isLoading: Boolean) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(Sizes.iconSmall),
            color = LocalContentColor.current,
            strokeWidth = Strokes.thick,
        )
        Spacer(Modifier.width(Spacing.space8))
    } else if (icon != null) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(Sizes.icon))
        Spacer(Modifier.width(Spacing.space8))
    }
    Text(text = text, style = MaterialTheme.typography.labelLarge)
}

@ThemePreviews
@Composable
private fun PrimaryButtonPreview() {
    AppPreview {
        PrimaryButton(
            text = "Sample text",
            onClick = {},
        )
    }
}

@ThemePreviews
@Composable
private fun SecondaryButtonPreview() {
    AppPreview {
        SecondaryButton(
            text = "Sample text",
            onClick = {},
        )
    }
}

@ThemePreviews
@Composable
private fun DangerButtonPreview() {
    AppPreview {
        DangerButton(
            text = "Sample text",
            onClick = {},
        )
    }
}

@ThemePreviews
@Composable
private fun AppTextButtonPreview() {
    AppPreview {
        AppTextButton(
            text = "Sample text",
            onClick = {},
        )
    }
}
