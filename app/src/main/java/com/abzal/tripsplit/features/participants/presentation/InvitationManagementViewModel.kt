package com.abzal.tripsplit.features.participants.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.domain.model.InvitationStatus
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InvitationManagementUiState(
    val trip: Trip? = null,
    val invitations: List<Invitation> = emptyList(),
    /** null shows all invitations. */
    val statusFilter: InvitationStatus? = null,
) {
    val visibleInvitations: List<Invitation>
        get() = invitations.filter { statusFilter == null || it.status == statusFilter }

    fun countOf(status: InvitationStatus): Int = invitations.count { it.status == status }
}

class InvitationManagementViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(InvitationManagementUiState())
    val uiState: StateFlow<InvitationManagementUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tripRepository.observeTrip(tripId).collect { value -> _uiState.update { it.copy(trip = value) } }
        }
        viewModelScope.launch {
            participantRepository.observeInvitations(tripId).collect { value -> _uiState.update { it.copy(invitations = value) } }
        }
    }

    fun onFilterChange(status: InvitationStatus?) = _uiState.update { it.copy(statusFilter = status) }

    fun revoke(invitationId: String) {
        viewModelScope.launch { participantRepository.revokeInvitation(invitationId) }
    }
}
