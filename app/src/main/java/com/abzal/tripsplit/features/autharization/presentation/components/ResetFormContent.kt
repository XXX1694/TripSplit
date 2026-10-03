package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.autharization.presentation.PasswordResetUiState
import com.abzal.tripsplit.features.autharization.presentation.samplePasswordResetUiState
import com.abzal.tripsplit.features.participants.presentation.components.label

@Composable
fun ResetFormContent(
    email: String,
    onEmailChange: (String) -> Unit,
    uiState: PasswordResetUiState,
    onSendClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    AppScaffold(
        topBar = { AppTopBar(title = "Reset password", onBackClick = onBackClick) },
        bottomBar = {
            BottomActionBar {
                PrimaryButton(
                    text = "Send reset link",
                    onClick = onSendClick,
                    enabled = email.contains("@"),
                    isLoading = uiState.isLoading,
                )
            }
        },
    ) {
        Text(
            text = "Enter the email of your account and we will send you a secure reset link.",
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.textSecondary,
        )
        AppTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email",
            leadingIcon = Icons.Outlined.Email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = uiState.error != null,
            supportingText = uiState.error,
        )
    }
}

// ---------- Step 2: "Check your inbox" ----------

@Preview(showBackground = true)
@Composable
private fun ResetFormContentPreview() {
    AppPreview {
        ResetFormContent(
            email = "maya@hey.com",
            onEmailChange = {},
            uiState = samplePasswordResetUiState,
            onSendClick = {},
            onBackClick = {},
        )
    }
}
