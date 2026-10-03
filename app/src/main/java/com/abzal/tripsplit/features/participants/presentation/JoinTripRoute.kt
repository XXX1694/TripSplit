package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun JoinTripRoute(
    onBackClick: () -> Unit,
    onJoined: (String) -> Unit,
    viewModel: JoinTripViewModel = injectedViewModel { c, _ -> JoinTripViewModel(c.participantRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    JoinTripScreen(
        uiState = uiState,
        onJoinClick = { code -> viewModel.join(code, onJoined) },
        onBackClick = onBackClick,
    )
}
