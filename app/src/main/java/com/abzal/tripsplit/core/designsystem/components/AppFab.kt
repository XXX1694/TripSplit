package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Elevations
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews

/** Green floating button with icon and text: "Create trip", "Add expense". */
@Composable
fun AppFab(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = AppTheme.colors.accent,
        contentColor = AppTheme.colors.onAccent,
        shape = MaterialTheme.shapes.large,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = Elevations.fab),
        icon = { Icon(icon, contentDescription = null) },
        text = { Text(text = text, style = MaterialTheme.typography.labelLarge) },
    )
}

@ThemePreviews
@Composable
private fun AppFabPreview() {
    AppPreview {
        AppFab(
            text = "Sample text",
            icon = Icons.Outlined.Star,
            onClick = {},
        )
    }
}
