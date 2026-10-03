package com.abzal.tripsplit.features.trips.domain.repository

import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.model.TripDraft
import kotlinx.coroutines.flow.Flow

interface TripRepository {
    fun observeTrips(): Flow<List<Trip>>
    fun observeTrip(tripId: String): Flow<Trip?>
    suspend fun createTrip(draft: TripDraft): Trip
    suspend fun updateTrip(trip: Trip)
    suspend fun deleteTrip(tripId: String)
}
