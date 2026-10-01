package com.abzal.tripsplit.features.trips.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.features.autharization.domain.model.*
import com.abzal.tripsplit.features.balances.domain.model.*
import com.abzal.tripsplit.features.expenses.domain.model.*
import com.abzal.tripsplit.features.insights.domain.model.*
import com.abzal.tripsplit.features.participants.domain.model.*
import com.abzal.tripsplit.features.trips.domain.model.*
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditTripUiState(
    val trip: Trip? = null,
    val isSaving: Boolean = false,
)

class EditTripViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(EditTripUiState())
    val uiState: StateFlow<EditTripUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tripRepository.observeTrip(tripId).collect { value -> _uiState.update { it.copy(trip = value) } }
        }
    }

    fun save(draft: TripDraft, onSaved: () -> Unit) {
        val current = _uiState.value.trip ?: return
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
