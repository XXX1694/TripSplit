package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.participants.presentation.components.label

/**
 * Text field with floating label, optional leading icon and trailing slot.
 * [supportingText] is shown below the field (red when [isError]).
 */
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val colors = AppTheme.colors
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        isError = isError,
        singleLine = true,
        label = { Text(label) },
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon?.let { { Icon(it, contentDescription = null) } },
        trailingIcon = trailingContent,
        supportingText = supportingText?.let { { Text(it) } },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        textStyle = MaterialTheme.typography.titleSmall,
        shape = MaterialTheme.shapes.large,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colors.primary,
            unfocusedBorderColor = colors.outline,
            errorBorderColor = colors.negative,
            focusedContainerColor = colors.surface,
            unfocusedContainerColor = colors.surface,
            errorContainerColor = colors.surface,
            focusedLabelColor = colors.primary,
            unfocusedLabelColor = colors.textSecondary,
            errorLabelColor = colors.negative,
            focusedLeadingIconColor = colors.primary,
            unfocusedLeadingIconColor = colors.textSecondary,
            errorLeadingIconColor = colors.negative,
            focusedTextColor = colors.textPrimary,
            unfocusedTextColor = colors.textPrimary,
            focusedSupportingTextColor = colors.textSecondary,
            unfocusedSupportingTextColor = colors.textSecondary,
            errorSupportingTextColor = colors.negative,
            cursorColor = colors.primary,
        ),
    )
}

/** [AppTextField] for passwords: hidden text with an eye button to show it. */
@Composable
fun AppPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    supportingText: String? = null,
    isError: Boolean = false,
) {
    var isVisible by remember { mutableStateOf(false) }
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier,
        leadingIcon = Icons.Outlined.Lock,
        supportingText = supportingText,
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingContent = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(
                    imageVector = if (isVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = "Toggle password visibility",
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    AppPreview {
        AppTextField(
            value = "Value",
            onValueChange = {},
            label = "Label",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPasswordFieldPreview() {
    AppPreview {
        AppPasswordField(
            value = "Value",
            onValueChange = {},
        )
    }
}
