package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun InviteParticipantsRoute(
    onBackClick: () -> Unit,
    viewModel: InviteParticipantsViewModel = injectedViewModel { c, h ->
        InviteParticipantsViewModel(h, c.tripRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    InviteParticipantsScreen(
        uiState = uiState,
        onCreateInvitationClick = viewModel::createInvitation,
        onBackClick = onBackClick,
    )
}
