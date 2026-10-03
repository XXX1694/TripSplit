package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview

@Composable
fun EmptyHint(text: String, modifier: Modifier = Modifier) {
    Text(text, modifier = modifier, style = MaterialTheme.typography.bodyMedium, color = AppTheme.colors.textSecondary)
}

@Preview(showBackground = true)
@Composable
private fun EmptyHintPreview() {
    AppPreview {
        EmptyHint(
            text = "Sample text",
        )
    }
}
