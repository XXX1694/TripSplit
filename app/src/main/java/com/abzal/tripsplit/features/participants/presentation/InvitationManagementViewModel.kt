package com.abzal.tripsplit.features.participants.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

data class InvitationManagementUiState(
    val invitations: List<Invitation> = emptyList(),
)

class InvitationManagementViewModel(
    savedStateHandle: SavedStateHandle,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(InvitationManagementUiState())
    val uiState: StateFlow<InvitationManagementUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            participantRepository.observeInvitations(tripId).collect { value -> _uiState.update { it.copy(invitations = value) } }
        }
    }

    fun revoke(invitationId: String) {
        viewModelScope.launch { participantRepository.revokeInvitation(invitationId) }
    }
}
