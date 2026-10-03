package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.Strokes
import com.abzal.tripsplit.core.designsystem.components.DangerButton
import com.abzal.tripsplit.core.designsystem.components.SecondaryButton
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview

/** Red box that asks to confirm the deletion. */
@Composable
fun DeleteExpenseConfirmation(
    expenseTitle: String,
    participantCount: Int,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = colors.negativeContainer,
        border = BorderStroke(Strokes.thin, colors.negative),
    ) {
        Column(modifier = Modifier.padding(Spacing.md), verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                Icon(Icons.Outlined.WarningAmber, contentDescription = null, tint = colors.negative)
                Text("Delete $expenseTitle?", style = MaterialTheme.typography.titleMedium, color = colors.negative)
            }
            Text(
                text = "All $participantCount balances will be recalculated. This action cannot be undone.",
                style = MaterialTheme.typography.bodySmall,
                color = colors.negative,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                SecondaryButton(text = "Cancel", onClick = onCancelClick, modifier = Modifier.weight(1f))
                DangerButton(text = "Delete expense", icon = Icons.Outlined.Delete, onClick = onDeleteClick, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DeleteExpenseConfirmationPreview() {
    AppPreview {
        DeleteExpenseConfirmation(
            expenseTitle = "Dinner at Prado",
            participantCount = 4,
            onCancelClick = {},
            onDeleteClick = {},
        )
    }
}
