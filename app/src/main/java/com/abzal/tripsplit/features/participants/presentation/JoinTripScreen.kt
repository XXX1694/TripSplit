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
fun JoinTripRoute(
    onBackClick: () -> Unit,
    onJoined: (String) -> Unit,
    viewModel: JoinTripViewModel = injectedViewModel { c, h -> JoinTripViewModel(c.participantRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    JoinTripScreen(
        uiState = uiState,
        onJoinClick = { code -> viewModel.join(code, onJoined) },
        onBackClick = onBackClick,
    )
}

@Composable
fun JoinTripScreen(
    uiState: JoinTripUiState,
    onJoinClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "JoinTrip")
    }
}
