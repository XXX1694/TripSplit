package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.abzal.tripsplit.core.designsystem.components.AppPasswordField
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.presentation.components.label

@Composable
fun SignUpFields(
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

@ThemePreviews
@Composable
private fun SignUpFieldsPreview() {
    AppPreview {
        SignUpFields(
            name = "Maya Kim",
            onNameChange = {},
            email = "maya@hey.com",
            onEmailChange = {},
            password = "secret123",
            onPasswordChange = {},
            confirmPassword = "secret123",
            onConfirmPasswordChange = {},
        )
    }
}
