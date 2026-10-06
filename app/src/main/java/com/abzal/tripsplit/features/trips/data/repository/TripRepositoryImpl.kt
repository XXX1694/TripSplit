package com.abzal.tripsplit.features.trips.data.repository

import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.model.TripDraft
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TripRepositoryImpl(initialTrips: List<Trip> = emptyList()) : TripRepository {
    private val trips = MutableStateFlow(initialTrips)

    override fun observeTrips(): Flow<List<Trip>> = trips

    override fun observeTrip(tripId: String): Flow<Trip?> =
        trips.map { list -> list.firstOrNull { it.id == tripId } }

    override suspend fun createTrip(draft: TripDraft): Trip {
        val trip = Trip(
            id = UUID.randomUUID().toString(),
            name = draft.name,
            currency = draft.currency,
            startDateMillis = draft.startDateMillis,
            endDateMillis = draft.endDateMillis,
            destination = draft.destination,
            cover = draft.cover,
        )
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
