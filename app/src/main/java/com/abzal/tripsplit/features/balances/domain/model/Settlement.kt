package com.abzal.tripsplit.features.balances.domain.model

import com.abzal.tripsplit.core.navigation.tripId
import java.util.UUID

data class Settlement(
    val id: String = UUID.randomUUID().toString(),
    val tripId: String,
    val fromId: String,
    val toId: String,
    val amount: Double,
    val currency: String,
    val dateMillis: Long = System.currentTimeMillis(),
)

/** Suggested payment from the optimized settlement plan. */
data class Transfer(
    val fromId: String,
    val toId: String,
    val amount: Double,
)
