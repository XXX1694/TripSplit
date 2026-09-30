package com.abzal.tripsplit.features.balances.domain.model

/** Positive [amount] — participant is owed money, negative — participant owes. */
data class Balance(
    val participantId: String,
    val amount: Double,
)
