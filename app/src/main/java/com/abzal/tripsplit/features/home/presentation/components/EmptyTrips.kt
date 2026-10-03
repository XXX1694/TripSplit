package com.abzal.tripsplit.features.home.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview

@Composable
fun EmptyTrips() {
    Text(
        text = "No trips yet. Create your first trip or join one with an invitation code.",
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyMedium,
        color = AppTheme.colors.textSecondary,
        textAlign = TextAlign.Center,
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyTripsPreview() {
    AppPreview {
        EmptyTrips()
    }
}
