package com.abzal.tripsplit.features.participants.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.DangerButton
import com.abzal.tripsplit.core.designsystem.components.IconBadge
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.features.participants.domain.model.Participant

/** Quick add: an email field and the "Add" button. */
@Composable
fun QuickAddParticipantCard(
    email: String,
    onEmailChange: (String) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(modifier = modifier) {
        AppListRow(
            title = "Add someone",
            subtitle = "Invite by email or add without an account",
            leading = { IconBadge(Icons.Outlined.PersonAdd) },
        )
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.xs), verticalAlignment = Alignment.CenterVertically) {
            AppTextField(
                value = email,
                onValueChange = onEmailChange,
                label = "name@email.com",
                leadingIcon = Icons.Outlined.Email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.weight(1f),
            )
            PrimaryButton(text = "Add", onClick = onAddClick, enabled = email.contains("@"), modifier = Modifier.weight(0.4f))
        }
    }
}

/** Avatar, name, email and a pencil button. */
@Composable
fun ParticipantRow(
    participant: Participant,
    toneIndex: Int,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppListRow(
        title = participant.name,
        subtitle = participant.email,
        modifier = modifier,
        leading = { Avatar(initials = participant.name.toInitials(), tone = avatarToneAt(toneIndex), size = 48.dp) },
        trailing = {
            IconButton(onClick = onEditClick) { Icon(Icons.Outlined.Edit, contentDescription = "Edit") }
        },
    )
}

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
        border = BorderStroke(1.dp, colors.warning),
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
