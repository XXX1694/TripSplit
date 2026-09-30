package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.autharization.domain.model.*
import com.abzal.tripsplit.features.balances.domain.model.*
import com.abzal.tripsplit.features.expenses.domain.model.*
import com.abzal.tripsplit.features.insights.domain.model.*
import com.abzal.tripsplit.features.participants.domain.model.*
import com.abzal.tripsplit.features.trips.domain.model.*

@Composable
fun ParticipantManagementRoute(
    onBackClick: () -> Unit,
    onAddParticipantClick: () -> Unit,
    onInviteClick: () -> Unit,
    onInvitationsClick: () -> Unit,
    viewModel: ParticipantManagementViewModel = injectedViewModel { c, h -> ParticipantManagementViewModel(h, c.participantRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ParticipantManagementScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onAddParticipantClick = onAddParticipantClick,
        onInviteClick = onInviteClick,
        onInvitationsClick = onInvitationsClick,
        onRemoveClick = viewModel::remove,
    )
}

@Composable
fun ParticipantManagementScreen(
    uiState: ParticipantManagementUiState,
    onBackClick: () -> Unit,
    onAddParticipantClick: () -> Unit,
    onInviteClick: () -> Unit,
    onInvitationsClick: () -> Unit,
    onRemoveClick: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "ParticipantManagement")
    }
}
