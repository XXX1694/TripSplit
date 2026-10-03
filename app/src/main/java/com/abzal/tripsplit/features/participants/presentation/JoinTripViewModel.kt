package com.abzal.tripsplit.features.participants.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.autharization.domain.model.*
import com.abzal.tripsplit.features.balances.domain.model.*
import com.abzal.tripsplit.features.expenses.domain.model.*
import com.abzal.tripsplit.features.insights.domain.model.*
import com.abzal.tripsplit.features.participants.domain.model.*
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class JoinTripUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
)

class JoinTripViewModel(
    private val participantRepository: ParticipantRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(JoinTripUiState())
    val uiState: StateFlow<JoinTripUiState> = _uiState.asStateFlow()

    fun join(code: String, onJoined: (String) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            participantRepository.joinByCode(code)
                .onSuccess { tripId ->
                    _uiState.update { it.copy(isLoading = false) }
                    onJoined(tripId)
                }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }
}
