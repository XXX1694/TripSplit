package com.abzal.tripsplit.features.trips.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.trips.domain.model.TripDraft
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreateTripUiState(
    val draft: TripDraft = TripDraft(),
    val isSaving: Boolean = false,
)

class CreateTripViewModel(
    private val tripRepository: TripRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateTripUiState())
    val uiState: StateFlow<CreateTripUiState> = _uiState.asStateFlow()

    fun onDraftChange(draft: TripDraft) {
        _uiState.update { it.copy(draft = draft) }
    }

    fun onCurrencyPicked(currency: String) {
        _uiState.update { it.copy(draft = it.draft.copy(currency = currency)) }
    }

    fun createTrip(onCreated: (tripId: String) -> Unit) {
        val draft = _uiState.value.draft
        if (!draft.isValid) return
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            val trip = tripRepository.createTrip(draft)
            _uiState.update { it.copy(isSaving = false) }
            onCreated(trip.id)
        }
    }
}
