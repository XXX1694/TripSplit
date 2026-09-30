package com.abzal.tripsplit.features.balances.presentation

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
fun BalancesRoute(
    onBackClick: () -> Unit,
    onOptimizedClick: () -> Unit,
    onRecordSettlementClick: () -> Unit,
    viewModel: BalancesViewModel = injectedViewModel { c, h -> BalancesViewModel(h, c.balanceRepository, c.participantRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BalancesScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onOptimizedClick = onOptimizedClick,
        onRecordSettlementClick = onRecordSettlementClick,
    )
}

@Composable
fun BalancesScreen(
    uiState: BalancesUiState,
    onBackClick: () -> Unit,
    onOptimizedClick: () -> Unit,
    onRecordSettlementClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Balances")
    }
}
