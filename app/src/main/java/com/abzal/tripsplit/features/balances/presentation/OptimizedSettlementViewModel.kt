package com.abzal.tripsplit.features.balances.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.features.autharization.domain.model.*
import com.abzal.tripsplit.features.balances.domain.model.*
import com.abzal.tripsplit.features.balances.domain.repository.BalanceRepository
import com.abzal.tripsplit.features.expenses.domain.model.*
import com.abzal.tripsplit.features.insights.domain.model.*
import com.abzal.tripsplit.features.participants.domain.model.*
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OptimizedSettlementUiState(
    val transfers: List<Transfer> = emptyList(),
    val participants: List<Participant> = emptyList(),
)

class OptimizedSettlementViewModel(
    savedStateHandle: SavedStateHandle,
    private val balanceRepository: BalanceRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(OptimizedSettlementUiState())
    val uiState: StateFlow<OptimizedSettlementUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            balanceRepository.observeOptimizedTransfers(tripId).collect { value -> _uiState.update { it.copy(transfers = value) } }
        }
        viewModelScope.launch {
            participantRepository.observeParticipants(tripId).collect { value -> _uiState.update { it.copy(participants = value) } }
        }
    }
}
