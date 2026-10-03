package com.abzal.tripsplit.features.participants.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ParticipantManagementUiState(
    val trip: Trip? = null,
    val participants: List<Participant> = emptyList(),
    val newEmail: String = "",
    /** Participant that is being edited in the orange panel, if any. */
    val editingId: String? = null,
    val editingName: String = "",
) {
    val editingParticipant: Participant? get() = participants.firstOrNull { it.id == editingId }
}

class ParticipantManagementViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(ParticipantManagementUiState())
    val uiState: StateFlow<ParticipantManagementUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tripRepository.observeTrip(tripId).collect { value -> _uiState.update { it.copy(trip = value) } }
        }
        viewModelScope.launch {
            participantRepository.observeParticipants(tripId).collect { value -> _uiState.update { it.copy(participants = value) } }
        }
    }

    fun onEmailChange(email: String) = _uiState.update { it.copy(newEmail = email) }

    /** Quick add: the part of the email before "@" becomes the display name. */
    fun addByEmail() {
        val email = _uiState.value.newEmail.trim()
        if (!email.contains("@")) return
        viewModelScope.launch {
            participantRepository.addParticipant(tripId, email.substringBefore('@'), email)
            _uiState.update { it.copy(newEmail = "") }
        }
    }

    fun startEditing(participant: Participant) =
        _uiState.update { it.copy(editingId = participant.id, editingName = participant.name) }

    fun cancelEditing() = _uiState.update { it.copy(editingId = null, editingName = "") }

    fun onEditingNameChange(name: String) = _uiState.update { it.copy(editingName = name) }

    fun saveEditing() {
        val state = _uiState.value
        val id = state.editingId ?: return
        if (state.editingName.isBlank()) return
        viewModelScope.launch {
            participantRepository.renameParticipant(id, state.editingName.trim())
            cancelEditing()
        }
    }

    fun removeEditing() {
        val id = _uiState.value.editingId ?: return
        viewModelScope.launch {
            participantRepository.removeParticipant(id)
            cancelEditing()
        }
    }
}
