package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppDivider
import com.abzal.tripsplit.core.designsystem.components.AppOverflowMenu
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.MenuItem
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.participants.presentation.components.EditParticipantPanel
import com.abzal.tripsplit.features.participants.presentation.components.ParticipantRow
import com.abzal.tripsplit.features.participants.presentation.components.QuickAddParticipantCard

@Composable
fun ParticipantManagementScreen(
    uiState: ParticipantManagementUiState,
    onBackClick: () -> Unit,
    onAddParticipantClick: () -> Unit,
    onInviteClick: () -> Unit,
    onInvitationsClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onQuickAddClick: () -> Unit,
    onEditClick: (Participant) -> Unit,
    onEditingNameChange: (String) -> Unit,
    onCancelEditClick: () -> Unit,
    onSaveEditClick: () -> Unit,
    onRemoveClick: () -> Unit,
) {
    val editing = uiState.editingParticipant

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Participants",
                subtitle = "${uiState.trip?.name.orEmpty()} · ${uiState.participants.size} people",
                onBackClick = onBackClick,
                actions = {
                    AppOverflowMenu(
                        listOf(
                            MenuItem("Add manually", onAddParticipantClick),
                            MenuItem("Invite with a code", onInviteClick),
                            MenuItem("Invitations", onInvitationsClick),
                        ),
                    )
                },
            )
        },
    ) {
        QuickAddParticipantCard(email = uiState.newEmail, onEmailChange = onEmailChange, onAddClick = onQuickAddClick)

        AppCard(verticalArrangement = Arrangement.spacedBy(Spacing.xxs)) {
            uiState.participants.forEachIndexed { index, participant ->
                if (index > 0) AppDivider()
                ParticipantRow(participant = participant, toneIndex = index, onEditClick = { onEditClick(participant) })
            }
        }

        if (editing != null) {
            EditParticipantPanel(
                participantName = editing.name,
                editingName = uiState.editingName,
                onNameChange = onEditingNameChange,
                onCancelClick = onCancelEditClick,
                onRemoveClick = onRemoveClick,
                onSaveClick = onSaveEditClick,
            )
        }

        Text(
            text = "Removing someone does not delete expenses they already joined.",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodySmall,
            color = AppTheme.colors.textDisabled,
            textAlign = TextAlign.Center,
        )
    }
}

@ThemePreviews
@Composable
private fun ParticipantManagementScreenPreview() {
    AppPreview {
        ParticipantManagementScreen(
            uiState = sampleParticipantManagementUiState,
            onBackClick = {},
            onAddParticipantClick = {},
            onInviteClick = {},
            onInvitationsClick = {},
            onEmailChange = {},
            onQuickAddClick = {},
            onEditClick = {},
            onEditingNameChange = {},
            onCancelEditClick = {},
            onSaveEditClick = {},
            onRemoveClick = {},
        )
    }
}
