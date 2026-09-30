package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme

/** Section title with optional green action on the right: "Balances  See all". */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium, color = AppTheme.colors.textPrimary)
        if (actionText != null && onActionClick != null) {
            AppTextButton(text = actionText, onClick = onActionClick)
        }
    }
}

/** Small uppercase caption above a group: "PREFERENCES", "CATEGORY". */
@Composable
fun Overline(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text.uppercase(),
        modifier = modifier,
        style = MaterialTheme.typography.labelSmall,
        color = AppTheme.colors.textSecondary,
    )
}
