package com.abzal.tripsplit.features.trips.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.model.TripDraft
import com.abzal.tripsplit.features.trips.domain.model.toDraft
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditTripUiState(
    val draft: TripDraft = TripDraft(),
    val isLoaded: Boolean = false,
    val isSaving: Boolean = false,
)

class EditTripViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])
    private var trip: Trip? = null

    private val _uiState = MutableStateFlow(EditTripUiState())
    val uiState: StateFlow<EditTripUiState> = _uiState.asStateFlow()

    init {
        // The form is filled once; later repository updates must not overwrite what the user types.
        viewModelScope.launch {
            val loaded = tripRepository.observeTrip(tripId).filterNotNull().first()
            trip = loaded
            _uiState.update { it.copy(draft = loaded.toDraft(), isLoaded = true) }
        }
    }

    fun onDraftChange(draft: TripDraft) {
        _uiState.update { it.copy(draft = draft) }
    }

    fun onCurrencyPicked(currency: String) {
        _uiState.update { it.copy(draft = it.draft.copy(currency = currency)) }
    }

    fun save(onSaved: () -> Unit) {
        val current = trip ?: return
        val draft = _uiState.value.draft
        if (!draft.isValid) return
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            tripRepository.updateTrip(
                current.copy(
                    name = draft.name,
                    destination = draft.destination,
                    currency = draft.currency,
                    startDateMillis = draft.startDateMillis,
                    endDateMillis = draft.endDateMillis,
                ),
            )
            _uiState.update { it.copy(isSaving = false) }
            onSaved()
        }
    }
}
