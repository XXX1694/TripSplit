package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppCheckboxRow
import com.abzal.tripsplit.core.designsystem.components.AppPasswordField
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.di.injectedViewModel

private const val MIN_PASSWORD_LENGTH = 8

@Composable
fun SignUpRoute(
    onSignedUp: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: SignUpViewModel = injectedViewModel { c, _ -> SignUpViewModel(c.authRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SignUpScreen(
        uiState = uiState,
        onSignUpClick = { name, email, password -> viewModel.signUp(name, email, password, onSignedUp) },
        onBackClick = onBackClick,
        onSignInClick = onBackClick,
    )
}

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
            InfoBanner(text = uiState.error, icon = Icons.Outlined.Shield, tone = Tone.Negative)
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

@Composable
private fun SignUpIntro() {
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

@Composable
private fun SignUpFields(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
) {
    val isPasswordTooShort = password.isNotEmpty() && password.length < MIN_PASSWORD_LENGTH
    val isConfirmWrong = confirmPassword.isNotEmpty() && confirmPassword != password

    AppTextField(
        value = name,
        onValueChange = onNameChange,
        label = "Full name",
        leadingIcon = Icons.Outlined.Person,
    )
    AppTextField(
        value = email,
        onValueChange = onEmailChange,
        label = "Email",
        leadingIcon = Icons.Outlined.Email,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    )
    AppPasswordField(
        value = password,
        onValueChange = onPasswordChange,
        isError = isPasswordTooShort,
        supportingText = when {
            isPasswordTooShort -> "Password must be at least $MIN_PASSWORD_LENGTH characters"
            password.isNotEmpty() -> "Strong password · ${password.length} characters"
            else -> null
        },
    )
    AppPasswordField(
        value = confirmPassword,
        onValueChange = onConfirmPasswordChange,
        label = "Confirm password",
        isError = isConfirmWrong,
        supportingText = if (isConfirmWrong) "Passwords do not match" else null,
    )
}

@Composable
private fun SignUpFooter(
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
                color = AppTheme.colors.textSecondary,
            )
            AppTextButton(text = "Sign in", onClick = onSignInClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    TripSplitTheme {
        SignUpScreen(
            uiState = SignUpUiState(),
            onSignUpClick = { _, _, _ -> },
            onBackClick = {},
            onSignInClick = {},
        )
    }
}
