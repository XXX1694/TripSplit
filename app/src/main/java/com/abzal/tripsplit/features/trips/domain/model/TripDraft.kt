package com.abzal.tripsplit.features.trips.domain.model

/** Values of the create / edit trip form. */
data class TripDraft(
    val name: String = "",
    val destination: String = "",
    val currency: String = "EUR",
    val startDateMillis: Long? = null,
    val endDateMillis: Long? = null,
    val cover: String? = null,
) {
    val isValid: Boolean
        get() = name.isNotBlank() &&
            (startDateMillis == null || endDateMillis == null || endDateMillis >= startDateMillis)
}

fun Trip.toDraft() = TripDraft(name, destination, currency, startDateMillis, endDateMillis, cover)
