package com.abzal.tripsplit.features.autharization.presentation.components

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.participants.presentation.components.tone

@Composable
fun ResetSentContent(
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

@Preview(showBackground = true)
@Composable
private fun ResetSentContentPreview() {
    AppPreview {
        ResetSentContent(
            email = "maya@hey.com",
            onResendClick = {},
            onBackClick = {},
            onBackToSignInClick = {},
        )
    }
}
