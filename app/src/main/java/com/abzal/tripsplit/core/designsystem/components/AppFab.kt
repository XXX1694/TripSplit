package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme

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
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        shape = MaterialTheme.shapes.large,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp),
        icon = { Icon(icon, contentDescription = null) },
        text = { Text(text = text, style = MaterialTheme.typography.labelLarge) },
    )
}
