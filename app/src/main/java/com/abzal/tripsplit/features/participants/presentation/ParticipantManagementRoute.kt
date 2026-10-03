package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

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
