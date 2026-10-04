package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews

@Composable
fun SignUpFooter(
    isLoading: Boolean,
    isEnabled: Boolean,
    onCreateClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    BottomActionBar {
        PrimaryButton(
            text = if (isLoading) "Creating account…" else "Create account",
            onClick = onCreateClick,
            enabled = isEnabled,
            isLoading = isLoading,
        )
        Text(
            text = "Encrypting your profile and preparing your trips",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodySmall,
            color = AppTheme.colors.textDisabled,
            textAlign = TextAlign.Center,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Already have an account?",
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.colors.textMuted,
            )
            AppTextButton(text = "Sign in", onClick = onSignInClick)
        }
    }
}

@ThemePreviews
@Composable
private fun SignUpFooterPreview() {
    AppPreview {
        SignUpFooter(
            isLoading = false,
            isEnabled = true,
            onCreateClick = {},
            onSignInClick = {},
        )
    }
}
