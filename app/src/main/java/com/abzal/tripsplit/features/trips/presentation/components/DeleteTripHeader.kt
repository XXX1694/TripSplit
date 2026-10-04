package com.abzal.tripsplit.features.trips.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.BigIconBadge
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews

@Composable
fun DeleteTripHeader(tripName: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        BigIconBadge(
            icon = Icons.Outlined.Delete,
            containerColor = AppTheme.colors.negativeContainer,
            contentColor = AppTheme.colors.negative,
        )
        Text(
            text = "Delete this trip?",
            style = MaterialTheme.typography.headlineLarge,
            color = AppTheme.colors.textPrimary,
        )
        Text(
            text = "$tripName will be permanently removed from every participant's device.",
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@ThemePreviews
@Composable
private fun DeleteTripHeaderPreview() {
    AppPreview {
        DeleteTripHeader(
            tripName = "Lisbon Friends 2026",
        )
    }
}
