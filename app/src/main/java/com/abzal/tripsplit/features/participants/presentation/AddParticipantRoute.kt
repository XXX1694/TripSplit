package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun AddParticipantRoute(
    onBackClick: () -> Unit,
    onAdded: () -> Unit,
    viewModel: AddParticipantViewModel = injectedViewModel { c, h ->
        AddParticipantViewModel(h, c.tripRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AddParticipantScreen(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onAddClick = { viewModel.add(onAdded) },
        onBackClick = onBackClick,
    )
}
