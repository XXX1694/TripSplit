package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.IconBadge
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.participants.presentation.components.tone

@Composable
fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    tone: Tone = Tone.Primary,
    trailing: @Composable () -> Unit,
    onClick: (() -> Unit)? = null,
) {
    AppListRow(
        title = title,
        subtitle = subtitle,
        modifier = modifier,
        onClick = onClick,
        leading = { IconBadge(icon = icon, tone = tone) },
        trailing = trailing,
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingsRowPreview() {
    AppPreview {
        SettingsRow(
            icon = Icons.Outlined.Star,
            title = "Title",
            subtitle = "Subtitle",
            trailing = { Text("Content") },
        )
    }
}
