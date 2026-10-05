package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.autharization.presentation.components.MIN_PASSWORD_LENGTH
import com.abzal.tripsplit.features.autharization.presentation.components.SignInFooter
import com.abzal.tripsplit.features.autharization.presentation.components.SignInForm
import com.abzal.tripsplit.features.autharization.presentation.components.SignInHeader
import com.abzal.tripsplit.features.participants.presentation.components.tone

@Composable
fun SignInScreen(
    uiState: SignInUiState,
    onSignInClick: (email: String, password: String) -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(true) }

    val isFormValid = email.contains("@") && password.length >= MIN_PASSWORD_LENGTH

    AppScaffold(
        topBar = { AppTopBar(title = "Sign in") },
        bottomBar = { SignInFooter(onSignUpClick = onSignUpClick) },
    ) {
        SignInHeader()
        AppCard {
            SignInForm(
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                rememberMe = rememberMe,
                onRememberMeChange = { rememberMe = it },
                onForgotPasswordClick = onForgotPasswordClick,
            )
            if (uiState.error != null) {
                InfoBanner(text = uiState.error, icon = Icons.Outlined.Lock, tone = Tone.Danger)
            }
            PrimaryButton(
                text = "Sign in securely",
                icon = Icons.AutoMirrored.Outlined.Login,
                isLoading = uiState.isLoading,
                enabled = isFormValid,
                onClick = { onSignInClick(email, password) },
            )
        }
        InfoBanner(
            text = "Your trip data is encrypted and only visible to invited participants.",
            icon = Icons.Outlined.Shield,
            tone = Tone.Accent,
        )
    }
}

@ThemePreviews
@Composable
private fun SignInScreenPreview() {
    AppPreview {
        SignInScreen(
            uiState = sampleSignInUiState,
            onSignInClick = { _, _ -> },
            onSignUpClick = {},
            onForgotPasswordClick = {},
        )
    }
}
