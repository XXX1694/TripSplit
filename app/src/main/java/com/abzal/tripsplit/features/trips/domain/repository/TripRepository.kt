package com.abzal.tripsplit.features.trips.domain.repository

import com.abzal.tripsplit.features.trips.domain.model.Trip
import kotlinx.coroutines.flow.Flow

interface TripRepository {
    fun observeTrips(): Flow<List<Trip>>
    fun observeTrip(tripId: String): Flow<Trip?>
    suspend fun createTrip(name: String, currency: String, startDateMillis: Long?, endDateMillis: Long?): Trip
    suspend fun updateTrip(trip: Trip)
    suspend fun deleteTrip(tripId: String)
}
