package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews

@Composable
fun SignUpIntro() {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.xxs)) {
        Text(
            text = "Start your next shared trip",
            style = MaterialTheme.typography.headlineMedium,
            color = AppTheme.colors.textPrimary,
        )
        Text(
            text = "Create a secure account for Lisbon Friends 2026 and future adventures.",
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.textSecondary,
        )
    }
}

@ThemePreviews
@Composable
private fun SignUpIntroPreview() {
    AppPreview {
        SignUpIntro()
    }
}
