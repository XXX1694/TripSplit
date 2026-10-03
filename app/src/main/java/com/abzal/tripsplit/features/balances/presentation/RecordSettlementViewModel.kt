package com.abzal.tripsplit.features.balances.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.balances.domain.model.Settlement
import com.abzal.tripsplit.features.balances.domain.repository.BalanceRepository
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Values of the record payment form. */
data class SettlementDraft(
    val fromId: String? = null,
    val toId: String? = null,
    val amountText: String = "",
    val dateMillis: Long = System.currentTimeMillis(),
) {
    val amount: Double? get() = amountText.replace(',', '.').toDoubleOrNull()

    val isValid: Boolean
        get() = fromId != null && toId != null && fromId != toId && (amount ?: 0.0) > 0.0
}

data class RecordSettlementUiState(
    val trip: Trip? = null,
    val participants: List<Participant> = emptyList(),
    val draft: SettlementDraft = SettlementDraft(),
    val isSaving: Boolean = false,
) {
    val currency: String get() = trip?.currency.orEmpty()

    fun nameOf(participantId: String?): String = participants.firstOrNull { it.id == participantId }?.name.orEmpty()

    fun toneIndexOf(participantId: String?): Int = participants.indexOfFirst { it.id == participantId }.coerceAtLeast(0)
}

class RecordSettlementViewModel(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val balanceRepository: BalanceRepository,
    private val participantRepository: ParticipantRepository,
) : ViewModel() {
    private val tripId: String = checkNotNull(savedStateHandle["tripId"])

    private val _uiState = MutableStateFlow(RecordSettlementUiState())
    val uiState: StateFlow<RecordSettlementUiState> = _uiState.asStateFlow()

    init {
        // The form starts with the first recommended payment of the optimized plan.
        viewModelScope.launch {
            val trip = tripRepository.observeTrip(tripId).filterNotNull().first()
            val participants = participantRepository.observeParticipants(tripId).first()
            val recommended = balanceRepository.observeOptimizedTransfers(tripId).first().firstOrNull()
            _uiState.update {
                it.copy(
                    trip = trip,
                    participants = participants,
                    draft = it.draft.copy(
                        fromId = recommended?.fromId ?: participants.getOrNull(0)?.id,
                        toId = recommended?.toId ?: participants.getOrNull(1)?.id,
                        amountText = recommended?.let { r -> "%.2f".format(r.amount) }.orEmpty(),
                    ),
                )
            }
        }
    }

    fun onDraftChange(draft: SettlementDraft) {
        _uiState.update { it.copy(draft = draft) }
    }

    fun record(onRecorded: () -> Unit) {
        val draft = _uiState.value.draft
        if (!draft.isValid) return
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            balanceRepository.recordSettlement(
                Settlement(
                    tripId = tripId,
                    fromId = checkNotNull(draft.fromId),
                    toId = checkNotNull(draft.toId),
                    amount = checkNotNull(draft.amount),
                    currency = _uiState.value.currency,
                    dateMillis = draft.dateMillis,
                ),
            )
            _uiState.update { it.copy(isSaving = false) }
            onRecorded()
        }
    }
}
