package com.abzal.tripsplit.features.expenses.presentation

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
fun TripOverviewRoute(
    onBackClick: () -> Unit,
    onEditTripClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onExpenseClick: (String) -> Unit,
    onExpenseHistoryClick: () -> Unit,
    onBalancesClick: () -> Unit,
    onParticipantsClick: () -> Unit,
    onInsightsClick: () -> Unit,
    viewModel: TripOverviewViewModel = injectedViewModel { c, h -> TripOverviewViewModel(h, c.tripRepository, c.expenseRepository, c.balanceRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TripOverviewScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onEditTripClick = onEditTripClick,
        onAddExpenseClick = onAddExpenseClick,
        onExpenseClick = onExpenseClick,
        onExpenseHistoryClick = onExpenseHistoryClick,
        onBalancesClick = onBalancesClick,
        onParticipantsClick = onParticipantsClick,
        onInsightsClick = onInsightsClick,
    )
}

@Composable
fun TripOverviewScreen(
    uiState: TripOverviewUiState,
    onBackClick: () -> Unit,
    onEditTripClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onExpenseClick: (String) -> Unit,
    onExpenseHistoryClick: () -> Unit,
    onBalancesClick: () -> Unit,
    onParticipantsClick: () -> Unit,
    onInsightsClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "TripOverview")
    }
}
