package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.presentation.components.label

@Composable
fun ResetSteps() {
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
        color = AppTheme.colors.textMuted,
        textAlign = TextAlign.Center,
    )
}

enum class StepState { Done, Current, Todo }

@ThemePreviews
@Composable
private fun ResetStepsPreview() {
    AppPreview {
        ResetSteps()
    }
}
