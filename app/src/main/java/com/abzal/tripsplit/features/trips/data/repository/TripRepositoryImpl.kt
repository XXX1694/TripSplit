package com.abzal.tripsplit.features.trips.data.repository

import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.UUID

class TripRepositoryImpl : TripRepository {
    private val trips = MutableStateFlow<List<Trip>>(emptyList())

    override fun observeTrips(): Flow<List<Trip>> = trips

    override fun observeTrip(tripId: String): Flow<Trip?> =
        trips.map { list -> list.firstOrNull { it.id == tripId } }

    override suspend fun createTrip(
        name: String,
        currency: String,
        startDateMillis: Long?,
        endDateMillis: Long?,
    ): Trip {
        val trip = Trip(UUID.randomUUID().toString(), name, currency, startDateMillis, endDateMillis)
        trips.update { it + trip }
        return trip
    }

    override suspend fun updateTrip(trip: Trip) {
        trips.update { list -> list.map { if (it.id == trip.id) trip else it } }
    }

    override suspend fun deleteTrip(tripId: String) {
        trips.update { list -> list.filterNot { it.id == tripId } }
    }
}
