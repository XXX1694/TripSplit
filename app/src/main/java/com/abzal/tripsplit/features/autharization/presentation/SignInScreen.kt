package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppCheckboxRow
import com.abzal.tripsplit.core.designsystem.components.AppPasswordField
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.BigIconBadge
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.SecondaryButton
import com.abzal.tripsplit.core.designsystem.components.Tone

private const val MIN_PASSWORD_LENGTH = 8

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
        bottomBar = { SignInFooter(onSignUpClick = onSignUpClick) },
    ) {
        Spacer(Modifier.statusBarsPadding().height(Spacing.xl))
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
                InfoBanner(text = uiState.error, icon = Icons.Outlined.Lock, tone = Tone.Negative)
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
            tone = Tone.Primary,
        )
    }
}

@Composable
private fun SignInHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        BigIconBadge(icon = Icons.Outlined.ReceiptLong)
        Spacer(Modifier.height(Spacing.xs))
        Text(
            text = "Welcome back",
            style = MaterialTheme.typography.headlineLarge,
            color = AppTheme.colors.textPrimary,
        )
        Text(
            text = "Sign in to keep your Lisbon plans, expenses, and balances in sync.",
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SignInForm(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    rememberMe: Boolean,
    onRememberMeChange: (Boolean) -> Unit,
    onForgotPasswordClick: () -> Unit,
) {
    val isPasswordTooShort = password.isNotEmpty() && password.length < MIN_PASSWORD_LENGTH

    AppTextField(
        value = email,
        onValueChange = onEmailChange,
        label = "Email",
        leadingIcon = Icons.Outlined.Email,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        supportingText = if (email.contains("@")) "Email looks good" else null,
    )
    AppPasswordField(
        value = password,
        onValueChange = onPasswordChange,
        isError = isPasswordTooShort,
        supportingText = if (isPasswordTooShort) "Password must be at least $MIN_PASSWORD_LENGTH characters" else null,
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppCheckboxRow(text = "Remember me", checked = rememberMe, onCheckedChange = onRememberMeChange)
        AppTextButton(text = "Forgot password?", onClick = onForgotPasswordClick)
    }
}

@Composable
private fun SignInFooter(onSignUpClick: () -> Unit) {
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

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    TripSplitTheme {
        SignInScreen(
            uiState = SignInUiState(),
            onSignInClick = { _, _ -> },
            onSignUpClick = {},
            onForgotPasswordClick = {},
        )
    }
}
