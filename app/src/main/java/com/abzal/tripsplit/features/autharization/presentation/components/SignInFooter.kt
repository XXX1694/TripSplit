package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.SecondaryButton
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews

@Composable
fun SignInFooter(onSignUpClick: () -> Unit) {
    BottomActionBar {
        Text(
            text = "New to shared travel expenses?",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )
        SecondaryButton(text = "Create an account", onClick = onSignUpClick)
        Text(
            text = "By continuing, you agree to our Terms and Privacy Policy.",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodySmall,
            color = AppTheme.colors.textDisabled,
            textAlign = TextAlign.Center,
        )
    }
}

@ThemePreviews
@Composable
private fun SignInFooterPreview() {
    AppPreview {
        SignInFooter(
            onSignUpClick = {},
        )
    }
}
