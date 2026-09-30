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
fun OptimizedSettlementRoute(
    onBackClick: () -> Unit,
    onRecordSettlementClick: () -> Unit,
    viewModel: OptimizedSettlementViewModel = injectedViewModel { c, h -> OptimizedSettlementViewModel(h, c.balanceRepository, c.participantRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    OptimizedSettlementScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onRecordSettlementClick = onRecordSettlementClick,
    )
}

@Composable
fun OptimizedSettlementScreen(
    uiState: OptimizedSettlementUiState,
    onBackClick: () -> Unit,
    onRecordSettlementClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "OptimizedSettlement")
    }
}
