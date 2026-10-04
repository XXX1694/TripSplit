package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppCheckboxRow
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.autharization.presentation.components.MIN_PASSWORD_LENGTH
import com.abzal.tripsplit.features.autharization.presentation.components.SignUpFields
import com.abzal.tripsplit.features.autharization.presentation.components.SignUpFooter
import com.abzal.tripsplit.features.autharization.presentation.components.SignUpIntro
import com.abzal.tripsplit.features.participants.presentation.components.tone

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onSignUpClick: (name: String, email: String, password: String) -> Unit,
    onBackClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }

    val isFormValid = name.isNotBlank() &&
        email.contains("@") &&
        password.length >= MIN_PASSWORD_LENGTH &&
        password == confirmPassword &&
        termsAccepted

    AppScaffold(
        topBar = {
            AppTopBar(title = "Create account", subtitle = "Your trips, settled simply", onBackClick = onBackClick)
        },
        bottomBar = {
            SignUpFooter(
                isLoading = uiState.isLoading,
                isEnabled = isFormValid,
                onCreateClick = { onSignUpClick(name, email, password) },
                onSignInClick = onSignInClick,
            )
        },
    ) {
        SignUpIntro()
        AppCard {
            SignUpFields(
                name = name,
                onNameChange = { name = it },
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                confirmPassword = confirmPassword,
                onConfirmPasswordChange = { confirmPassword = it },
            )
        }
        if (uiState.error != null) {
            InfoBanner(text = uiState.error, icon = Icons.Outlined.Shield, tone = Tone.Danger)
        }
        InfoBanner(
            text = "Use 8+ characters with a number and symbol. Never reuse a banking password.",
            icon = Icons.Outlined.Shield,
        )
        AppCheckboxRow(
            text = "I agree to the Terms of Service and Privacy Policy.",
            checked = termsAccepted,
            onCheckedChange = { termsAccepted = it },
        )
    }
}

@ThemePreviews
@Composable
private fun SignUpScreenPreview() {
    AppPreview {
        SignUpScreen(
            uiState = sampleSignUpUiState,
            onSignUpClick = { _, _, _ -> },
            onBackClick = {},
            onSignInClick = {},
        )
    }
}
