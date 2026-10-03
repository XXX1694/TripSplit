package com.abzal.tripsplit.features.participants.domain.model

import com.abzal.tripsplit.core.navigation.tripId

data class Participant(
    val id: String,
    val tripId: String,
    val name: String,
    val email: String? = null,
)
