package com.abzal.tripsplit.features.balances.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.balances.domain.model.Settlement
import com.abzal.tripsplit.features.balances.domain.model.Transfer
import com.abzal.tripsplit.features.balances.domain.repository.BalanceRepository
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OptimizedSettlementUiState(
    val trip: Trip? = null,
    val transfers: List<Transfer> = emptyList(),
    val participants: List<Participant> = emptyList(),
) {
    val currency: String get() = trip?.currency.orEmpty()

    val totalAmount: Double get() = transfers.sumOf { it.amount }

    fun nameOf(participantId: String): String = participants.firstOrNull { it.id == participantId }?.name.orEmpty()

    fun toneIndexOf(participantId: String): Int = participants.indexOfFirst { it.id == participantId }.coerceAtLeast(0)
}

class OptimizedSettlementViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val balanceRepository: BalanceRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(OptimizedSettlementUiState())
    val uiState: StateFlow<OptimizedSettlementUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tripRepository.observeTrip(tripId).collect { value -> _uiState.update { it.copy(trip = value) } }
        }
        viewModelScope.launch {
            balanceRepository.observeOptimizedTransfers(tripId).collect { value -> _uiState.update { it.copy(transfers = value) } }
        }
        viewModelScope.launch {
            participantRepository.observeParticipants(tripId).collect { value -> _uiState.update { it.copy(participants = value) } }
        }
    }

    /** Records the transfer as a settlement; the plan is recalculated and the card disappears. */
    fun markPaid(transfer: Transfer) {
        viewModelScope.launch {
            balanceRepository.recordSettlement(
                Settlement(
                    tripId = tripId,
                    fromId = transfer.fromId,
                    toId = transfer.toId,
                    amount = transfer.amount,
                    currency = _uiState.value.currency,
                ),
            )
        }
    }
}
