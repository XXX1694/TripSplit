package com.abzal.tripsplit.features.participants.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddParticipantUiState(
    val trip: Trip? = null,
    val name: String = "",
    val email: String = "",
    val isSaving: Boolean = false,
) {
    /** Name is required, email is optional but must look like an email when filled. */
    val isValid: Boolean get() = name.isNotBlank() && (email.isBlank() || email.contains("@"))
}

class AddParticipantViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(AddParticipantUiState())
    val uiState: StateFlow<AddParticipantUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tripRepository.observeTrip(tripId).collect { value -> _uiState.update { it.copy(trip = value) } }
        }
    }

    fun onNameChange(name: String) = _uiState.update { it.copy(name = name) }

    fun onEmailChange(email: String) = _uiState.update { it.copy(email = email) }

    fun add(onAdded: () -> Unit) {
        val state = _uiState.value
        if (!state.isValid) return
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            participantRepository.addParticipant(tripId, state.name.trim(), state.email.trim().ifEmpty { null })
            _uiState.update { it.copy(isSaving = false) }
            onAdded()
        }
    }
}
