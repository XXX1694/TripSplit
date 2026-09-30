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
fun InviteParticipantsRoute(
    onBackClick: () -> Unit,
    viewModel: InviteParticipantsViewModel = injectedViewModel { c, h -> InviteParticipantsViewModel(h, c.participantRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    InviteParticipantsScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onCreateInvitationClick = viewModel::createInvitation,
    )
}

@Composable
fun InviteParticipantsScreen(
    uiState: InviteParticipantsUiState,
    onBackClick: () -> Unit,
    onCreateInvitationClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "InviteParticipants")
    }
}
