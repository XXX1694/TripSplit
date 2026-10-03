package com.abzal.tripsplit.features.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun HomeRoute(
    onTripClick: (String) -> Unit,
    onEditTripClick: (String) -> Unit,
    onCreateTripClick: () -> Unit,
    onJoinTripClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: HomeViewModel = injectedViewModel { c, _ ->
        HomeViewModel(c.authRepository, c.tripRepository, c.expenseRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        onTripClick = onTripClick,
        onEditTripClick = onEditTripClick,
        onCreateTripClick = onCreateTripClick,
        onJoinTripClick = onJoinTripClick,
        onProfileClick = onProfileClick,
    )
}
