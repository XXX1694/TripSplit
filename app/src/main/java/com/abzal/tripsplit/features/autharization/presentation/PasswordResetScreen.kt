package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.autharization.presentation.components.ResetFormContent
import com.abzal.tripsplit.features.autharization.presentation.components.ResetSentContent

@Composable
fun PasswordResetScreen(
    uiState: PasswordResetUiState,
    onSendClick: (email: String) -> Unit,
    onBackClick: () -> Unit,
    onBackToSignInClick: () -> Unit,
) {
    var email by remember { mutableStateOf("") }

    if (uiState.isSent) {
        ResetSentContent(email = email, onResendClick = { onSendClick(email) }, onBackClick, onBackToSignInClick)
    } else {
        ResetFormContent(
            email = email,
            onEmailChange = { email = it },
            uiState = uiState,
            onSendClick = { onSendClick(email) },
            onBackClick = onBackClick,
        )
    }
}

@ThemePreviews
@Composable
private fun PasswordResetScreenPreview() {
    AppPreview {
        PasswordResetScreen(
            uiState = samplePasswordResetUiState,
            onSendClick = {},
            onBackClick = {},
            onBackToSignInClick = {},
        )
    }
}
