package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun InvitationManagementRoute(
    onBackClick: () -> Unit,
    onInviteClick: () -> Unit,
    viewModel: InvitationManagementViewModel = injectedViewModel { c, h ->
        InvitationManagementViewModel(h, c.tripRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    InvitationManagementScreen(
        uiState = uiState,
        onFilterChange = viewModel::onFilterChange,
        onRevokeClick = viewModel::revoke,
        onBackClick = onBackClick,
        onInviteClick = onInviteClick,
    )
}
