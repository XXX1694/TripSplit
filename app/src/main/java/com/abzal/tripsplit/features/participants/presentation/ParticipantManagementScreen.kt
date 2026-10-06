package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import com.abzal.tripsplit.core.designsystem.components.EmptyState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppOverflowMenu
import com.abzal.tripsplit.core.designsystem.components.AppLazyScaffold
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

    AppLazyScaffold(
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
        item {
            QuickAddParticipantCard(email = uiState.newEmail, onEmailChange = onEmailChange, onAddClick = onQuickAddClick)
        }
        if (uiState.participants.isEmpty()) {
            item {
                EmptyState(
                    title = "No participants yet",
                    message = "Add friends by email or invite them with a code.",
                    icon = Icons.Outlined.Groups,
                )
            }
        }
        itemsIndexed(uiState.participants, key = { _, participant -> participant.id }) { index, participant ->
            AppCard {
                ParticipantRow(participant = participant, toneIndex = index, onEditClick = { onEditClick(participant) })
            }
        }
        if (editing != null) {
            item {
                EditParticipantPanel(
                    participantName = editing.name,
                    editingName = uiState.editingName,
                    onNameChange = onEditingNameChange,
                    onCancelClick = onCancelEditClick,
                    onRemoveClick = onRemoveClick,
                    onSaveClick = onSaveEditClick,
                )
            }
        }
        item {
            Text(
                text = "Removing someone does not delete expenses they already joined.",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.colors.textDisabled,
                textAlign = TextAlign.Center,
            )
        }
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
