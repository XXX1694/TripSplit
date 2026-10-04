package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppChip
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import kotlinx.coroutines.delay

const val RESEND_SECONDS = 42

@Composable
fun ResendRow(onResendClick: () -> Unit) {
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

@ThemePreviews
@Composable
private fun ResendRowPreview() {
    AppPreview {
        ResendRow(
            onResendClick = {},
        )
    }
}
