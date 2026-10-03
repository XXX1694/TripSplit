package com.abzal.tripsplit.features.trips.presentation

import com.abzal.tripsplit.core.preview.sampleParticipants
import com.abzal.tripsplit.core.preview.sampleTrip
import com.abzal.tripsplit.features.trips.domain.model.TripDraft
import com.abzal.tripsplit.features.trips.domain.model.toDraft

// Sample UI states for @Preview.

val sampleTripDraft: TripDraft = sampleTrip.toDraft()

val sampleCreateTripUiState = CreateTripUiState(draft = sampleTripDraft)

val sampleEditTripUiState = EditTripUiState(draft = sampleTripDraft, isLoaded = true)

val sampleDeleteTripUiState = DeleteTripUiState(
    trip = sampleTrip,
    expenseCount = 18,
    participantNames = sampleParticipants.map { it.name },
    unsettledAmount = 257.95,
)
