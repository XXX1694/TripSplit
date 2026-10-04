package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.abzal.tripsplit.core.designsystem.components.AppCheckboxRow
import com.abzal.tripsplit.core.designsystem.components.AppPasswordField
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.presentation.components.label

@Composable
fun SignInForm(
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

@ThemePreviews
@Composable
private fun SignInFormPreview() {
    AppPreview {
        SignInForm(
            email = "maya@hey.com",
            onEmailChange = {},
            password = "secret123",
            onPasswordChange = {},
            rememberMe = false,
            onRememberMeChange = {},
            onForgotPasswordClick = {},
        )
    }
}
