package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppDivider
import com.abzal.tripsplit.core.designsystem.components.AppOverflowMenu
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.MenuItem
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.participants.presentation.components.EditParticipantPanel
import com.abzal.tripsplit.features.participants.presentation.components.ParticipantRow
import com.abzal.tripsplit.features.participants.presentation.components.QuickAddParticipantCard
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun ParticipantManagementRoute(
    onBackClick: () -> Unit,
    onAddParticipantClick: () -> Unit,
    onInviteClick: () -> Unit,
    onInvitationsClick: () -> Unit,
    viewModel: ParticipantManagementViewModel = injectedViewModel { c, h ->
        ParticipantManagementViewModel(h, c.tripRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ParticipantManagementScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onAddParticipantClick = onAddParticipantClick,
        onInviteClick = onInviteClick,
        onInvitationsClick = onInvitationsClick,
        onEmailChange = viewModel::onEmailChange,
        onQuickAddClick = viewModel::addByEmail,
        onEditClick = viewModel::startEditing,
        onEditingNameChange = viewModel::onEditingNameChange,
        onCancelEditClick = viewModel::cancelEditing,
        onSaveEditClick = viewModel::saveEditing,
        onRemoveClick = viewModel::removeEditing,
    )
}

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

@Preview(showBackground = true)
@Composable
private fun ParticipantManagementScreenPreview() {
    val participants = listOf(
        Participant("a", "1", "Maya Kim", "maya@hey.com"),
        Participant("b", "1", "Leo Evans", "leo.evans@gmail.com"),
    )
    TripSplitTheme {
        ParticipantManagementScreen(
            uiState = ParticipantManagementUiState(
                trip = Trip("1", "Lisbon Friends 2026", "EUR"),
                participants = participants,
                editingId = "b",
                editingName = "Leo Evans",
            ),
            onBackClick = {}, onAddParticipantClick = {}, onInviteClick = {}, onInvitationsClick = {},
            onEmailChange = {}, onQuickAddClick = {}, onEditClick = {}, onEditingNameChange = {},
            onCancelEditClick = {}, onSaveEditClick = {}, onRemoveClick = {},
        )
    }
}
