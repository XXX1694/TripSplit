package com.abzal.tripsplit.features.trips.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.balances.domain.repository.BalanceRepository
import com.abzal.tripsplit.features.expenses.domain.repository.ExpenseRepository
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DeleteTripUiState(
    val trip: Trip? = null,
    val expenseCount: Int = 0,
    val participantNames: List<String> = emptyList(),
    /** Sum of everything that is still owed between participants. */
    val unsettledAmount: Double = 0.0,
    val isDeleting: Boolean = false,
)

class DeleteTripViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val expenseRepository: ExpenseRepository,
    private val participantRepository: ParticipantRepository,
    private val balanceRepository: BalanceRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(DeleteTripUiState())
    val uiState: StateFlow<DeleteTripUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tripRepository.observeTrip(tripId).collect { trip -> _uiState.update { it.copy(trip = trip) } }
        }
        viewModelScope.launch {
            expenseRepository.observeExpenses(tripId).collect { expenses ->
                _uiState.update { it.copy(expenseCount = expenses.size) }
            }
        }
        viewModelScope.launch {
            participantRepository.observeParticipants(tripId).collect { participants ->
                _uiState.update { it.copy(participantNames = participants.map { p -> p.name }) }
            }
        }
        viewModelScope.launch {
            balanceRepository.observeBalances(tripId).collect { balances ->
                val unsettled = balances.filter { it.amount > 0 }.sumOf { it.amount }
                _uiState.update { it.copy(unsettledAmount = unsettled) }
            }
        }
    }

    fun delete(onDeleted: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isDeleting = true) }
            tripRepository.deleteTrip(tripId)
            onDeleted()
        }
    }
}
