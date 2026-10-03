package com.abzal.tripsplit.features.participants.domain.model

import com.abzal.tripsplit.core.navigation.tripId

data class Invitation(
    val id: String,
    val tripId: String,
    val code: String,
    val status: InvitationStatus = InvitationStatus.PENDING,
)

enum class InvitationStatus { PENDING, ACCEPTED, REVOKED }
