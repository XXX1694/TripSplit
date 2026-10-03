package com.abzal.tripsplit.features.participants.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.Strokes
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.DangerButton
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview

/** Orange panel to rename or remove the participant. */
@Composable
fun EditParticipantPanel(
    participantName: String,
    editingName: String,
    onNameChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        color = colors.warningContainer,
        border = BorderStroke(Strokes.thin, colors.warning),
    ) {
        Column(modifier = Modifier.padding(Spacing.md), verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Editing ${participantName.substringBefore(' ')}", style = MaterialTheme.typography.titleMedium)
                AppTextButton(text = "Cancel", onClick = onCancelClick)
            }
            AppTextField(value = editingName, onValueChange = onNameChange, label = "Display name")
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                DangerButton(text = "Remove", icon = Icons.Outlined.Delete, onClick = onRemoveClick, modifier = Modifier.weight(1f))
                PrimaryButton(
                    text = "Save",
                    icon = Icons.Outlined.Check,
                    onClick = onSaveClick,
                    enabled = editingName.isNotBlank(),
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditParticipantPanelPreview() {
    AppPreview {
        EditParticipantPanel(
            participantName = "Leo Evans",
            editingName = "Leo Evans",
            onNameChange = {},
            onCancelClick = {},
            onRemoveClick = {},
            onSaveClick = {},
        )
    }
}
