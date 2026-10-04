package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.Strokes
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.presentation.components.label

@Composable
fun ResetStep(number: Int, label: String, state: StepState) {
    val colors = AppTheme.colors
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = Spacing.space4)) {
        val circle = Modifier.size(Sizes.stepCircle).clip(CircleShape)
        Box(
            modifier = if (state == StepState.Todo) circle.border(Strokes.thin, colors.border, CircleShape) else circle.background(colors.accent),
            contentAlignment = Alignment.Center,
        ) {
            if (state == StepState.Done) {
                Icon(Icons.Outlined.Check, contentDescription = null, tint = colors.onAccent, modifier = Modifier.size(Sizes.iconSmall))
            } else {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = if (state == StepState.Todo) colors.textDisabled else colors.onAccent,
                )
            }
        }
        Text(
            text = label,
            modifier = Modifier.padding(top = Spacing.space4).width(Sizes.stepLabelWidth),
            style = MaterialTheme.typography.labelMedium,
            color = if (state == StepState.Todo) colors.textDisabled else colors.accent,
            textAlign = TextAlign.Center,
        )
    }
}

@ThemePreviews
@Composable
private fun ResetStepPreview() {
    AppPreview {
        ResetStep(
            number = 2,
            label = "Label",
            state = StepState.Current,
        )
    }
}
