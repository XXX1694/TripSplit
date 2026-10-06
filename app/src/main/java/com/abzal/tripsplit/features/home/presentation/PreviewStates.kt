package com.abzal.tripsplit.features.home.presentation

import com.abzal.tripsplit.core.preview.sampleParticipants
import com.abzal.tripsplit.core.preview.samplePastTrip
import com.abzal.tripsplit.core.preview.sampleTrip
import com.abzal.tripsplit.core.preview.sampleUser

val sampleTripSummaries: List<TripSummary> = listOf(
    TripSummary(sampleTrip, sampleParticipants.map { it.name }, totalSpent = 1284.6),
    TripSummary(samplePastTrip, sampleParticipants.take(2).map { it.name }, totalSpent = 218_400.0),
)

val sampleTripSummary = sampleTripSummaries.first()

val sampleHomeUiState = HomeUiState(userName = sampleUser.name, trips = sampleTripSummaries)
