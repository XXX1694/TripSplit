package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews

/** Friendly message for a list with nothing in it: icon, title, explanation and an optional action. */
@Composable
fun EmptyState(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Inbox,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(vertical = Spacing.space24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.space8),
    ) {
        IconBadge(icon = icon, size = Sizes.bigBadge)
        Text(text = title, style = MaterialTheme.typography.titleMedium, color = AppTheme.colors.text)
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.textMuted,
            textAlign = TextAlign.Center,
        )
        if (actionText != null && onActionClick != null) {
            AppTextButton(text = actionText, onClick = onActionClick)
        }
    }
}

@ThemePreviews
@Composable
private fun EmptyStatePreview() {
    AppPreview {
        EmptyState(
            title = "No trips yet",
            message = "Create your first trip or join one with an invitation code.",
            actionText = "Create trip",
            onActionClick = {},
        )
    }
}
