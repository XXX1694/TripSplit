package com.abzal.tripsplit.features.trips.domain.model

data class Trip(
    val id: String,
    val name: String,
    val currency: String,
    val startDateMillis: Long? = null,
    val endDateMillis: Long? = null,
    val destination: String = "",
    /** Key of the cover photo, see `coverImageRes`. */
    val cover: String? = null,
)
