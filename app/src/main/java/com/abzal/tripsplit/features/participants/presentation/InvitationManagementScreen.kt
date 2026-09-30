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
fun InvitationManagementRoute(
    onBackClick: () -> Unit,
    onInviteClick: () -> Unit,
    viewModel: InvitationManagementViewModel = injectedViewModel { c, h -> InvitationManagementViewModel(h, c.participantRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    InvitationManagementScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onInviteClick = onInviteClick,
        onRevokeClick = viewModel::revoke,
    )
}

@Composable
fun InvitationManagementScreen(
    uiState: InvitationManagementUiState,
    onBackClick: () -> Unit,
    onInviteClick: () -> Unit,
    onRevokeClick: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "InvitationManagement")
    }
}
