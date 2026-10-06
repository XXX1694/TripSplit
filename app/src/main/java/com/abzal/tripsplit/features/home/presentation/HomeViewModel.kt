package com.abzal.tripsplit.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.features.autharization.domain.repository.AuthRepository
import com.abzal.tripsplit.features.expenses.domain.repository.ExpenseRepository
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Trip with everything a card on the home screen shows. */
data class TripSummary(
    val trip: Trip,
    val participantNames: List<String>,
    val totalSpent: Double,
)

data class HomeUiState(
    val userName: String = "",
    val trips: List<TripSummary> = emptyList(),
) {
    /** Trip that is still going on (or has no end date). */
    val activeTrip: TripSummary?
        get() = trips.firstOrNull { it.trip.endDateMillis == null || it.trip.endDateMillis >= System.currentTimeMillis() }

    val pastTrips: List<TripSummary>
        get() = trips - listOfNotNull(activeTrip).toSet()
}

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val authRepository: AuthRepository,
    private val tripRepository: TripRepository,
    private val expenseRepository: ExpenseRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                _uiState.update { it.copy(userName = user?.name.orEmpty()) }
            }
        }
        viewModelScope.launch {
            observeTripSummaries().collect { summaries ->
                _uiState.update { it.copy(trips = summaries) }
            }
        }
    }

    // One combined flow per trip (expenses + participants); restarts when the list of trips changes.
    private fun observeTripSummaries() = tripRepository.observeTrips().flatMapLatest { trips ->
        if (trips.isEmpty()) {
            flowOf(emptyList())
        } else {
            combine(trips.map { trip -> observeSummary(trip) }) { it.toList() }
        }
    }

    private fun observeSummary(trip: Trip) = combine(
        expenseRepository.observeExpenses(trip.id),
        participantRepository.observeParticipants(trip.id),
    ) { expenses, participants ->
        TripSummary(
            trip = trip,
            participantNames = participants.map { it.name },
            totalSpent = expenses.sumOf { it.amount },
        )
    }
}
