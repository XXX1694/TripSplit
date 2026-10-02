package com.abzal.tripsplit.features.participants.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InviteParticipantsUiState(
    val trip: Trip? = null,
    val invitation: Invitation? = null,
)

class InviteParticipantsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(InviteParticipantsUiState())
    val uiState: StateFlow<InviteParticipantsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tripRepository.observeTrip(tripId).collect { value -> _uiState.update { it.copy(trip = value) } }
        }
    }

    fun createInvitation() {
        viewModelScope.launch {
            val invitation = participantRepository.createInvitation(tripId)
            _uiState.update { it.copy(invitation = invitation) }
        }
    }
}
