package com.abzal.tripsplit.features.autharization.presentation

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.border
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppChip
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BigIconBadge
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.StatusPill
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.di.injectedViewModel
import kotlinx.coroutines.delay

private const val RESEND_SECONDS = 42

@Composable
fun PasswordResetRoute(
    onBackClick: () -> Unit,
    onBackToSignInClick: () -> Unit,
    viewModel: PasswordResetViewModel = injectedViewModel { c, _ -> PasswordResetViewModel(c.authRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PasswordResetScreen(
        uiState = uiState,
        onSendClick = viewModel::requestReset,
        onBackClick = onBackClick,
        onBackToSignInClick = onBackToSignInClick,
    )
}

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

// ---------- Step 1: enter email ----------

@Composable
private fun ResetFormContent(
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

@Composable
private fun ResetSentContent(
    email: String,
    onResendClick: () -> Unit,
    onBackClick: () -> Unit,
    onBackToSignInClick: () -> Unit,
) {
    val context = LocalContext.current
    val openEmailApp = {
        runCatching {
            val intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
        Unit
    }

    AppScaffold(
        topBar = { AppTopBar(title = "Reset password", onBackClick = onBackClick) },
        bottomBar = {
            BottomActionBar {
                PrimaryButton(text = "Open email app", icon = Icons.AutoMirrored.Outlined.OpenInNew, onClick = openEmailApp)
                AppTextButton(
                    text = "Back to sign in",
                    onClick = onBackToSignInClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
    ) {
        ResetSentHeader(email)
        AppCard { ResetSteps() }
        InfoBanner(
            text = "For your security, the link expires in 15 minutes and works once.",
            icon = Icons.Outlined.Schedule,
            tone = Tone.Warning,
        )
        ResendRow(onResendClick)
    }
}

@Composable
private fun ResetSentHeader(email: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        BigIconBadge(icon = Icons.Outlined.MarkEmailRead)
        StatusPill(text = "Email sent", icon = Icons.Outlined.CheckCircle)
        Text(
            text = "Check your inbox",
            style = MaterialTheme.typography.headlineLarge,
            color = AppTheme.colors.textPrimary,
        )
        Text(
            text = buildAnnotatedString {
                append("We sent a secure reset link to ")
                withStyle(SpanStyle(fontWeight = FontWeight.SemiBold, color = AppTheme.colors.textPrimary)) { append(email) }
                append(".")
            },
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ResetSteps() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        ResetStep(number = 1, label = "Email sent", state = StepState.Done)
        StepLine(isActive = true, modifier = Modifier.weight(1f))
        ResetStep(number = 2, label = "Choose password", state = StepState.Current)
        StepLine(isActive = false, modifier = Modifier.weight(1f))
        ResetStep(number = 3, label = "Sign in", state = StepState.Todo)
    }
    Text(
        text = "Open the link on this device, then choose a new password.",
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodySmall,
        color = AppTheme.colors.textSecondary,
        textAlign = TextAlign.Center,
    )
}

private enum class StepState { Done, Current, Todo }

@Composable
private fun ResetStep(number: Int, label: String, state: StepState) {
    val colors = AppTheme.colors
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = Spacing.xxs)) {
        val circle = Modifier.size(32.dp).clip(CircleShape)
        Box(
            modifier = if (state == StepState.Todo) circle.border(1.dp, colors.outline, CircleShape) else circle.background(colors.primary),
            contentAlignment = Alignment.Center,
        ) {
            if (state == StepState.Done) {
                Icon(Icons.Outlined.Check, contentDescription = null, tint = colors.onPrimary, modifier = Modifier.size(18.dp))
            } else {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = if (state == StepState.Todo) colors.textDisabled else colors.onPrimary,
                )
            }
        }
        Text(
            text = label,
            modifier = Modifier.padding(top = Spacing.xxs).width(72.dp),
            style = MaterialTheme.typography.labelMedium,
            color = if (state == StepState.Todo) colors.textDisabled else colors.primary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun StepLine(isActive: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(top = 15.dp)
            .height(2.dp)
            .background(if (isActive) AppTheme.colors.primary else AppTheme.colors.track),
    )
}

@Composable
private fun ResendRow(onResendClick: () -> Unit) {
    var secondsLeft by remember { mutableIntStateOf(RESEND_SECONDS) }
    var resendCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(resendCount) {
        secondsLeft = RESEND_SECONDS
        while (secondsLeft > 0) {
            delay(1000)
            secondsLeft--
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Text(
            text = "Didn't receive it? Check spam or",
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.textSecondary,
        )
        AppChip(
            text = if (secondsLeft > 0) "Resend available in %02d:%02d".format(secondsLeft / 60, secondsLeft % 60) else "Resend email",
            icon = Icons.Outlined.Refresh,
            selected = false,
            onClick = {
                if (secondsLeft == 0) {
                    onResendClick()
                    resendCount++
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordResetFormPreview() {
    TripSplitTheme {
        PasswordResetScreen(PasswordResetUiState(), onSendClick = {}, onBackClick = {}, onBackToSignInClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordResetSentPreview() {
    TripSplitTheme {
        PasswordResetScreen(PasswordResetUiState(isSent = true), onSendClick = {}, onBackClick = {}, onBackToSignInClick = {})
    }
}
