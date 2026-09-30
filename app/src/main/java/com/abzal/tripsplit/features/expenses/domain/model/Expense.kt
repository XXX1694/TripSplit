package com.abzal.tripsplit.features.expenses.domain.model

import java.util.UUID

data class Expense(
    val id: String = UUID.randomUUID().toString(),
    val tripId: String,
    val title: String,
    val amount: Double,
    val currency: String,
    val paidById: String,
    val participantIds: List<String>,
    val category: String = "Other",
    val dateMillis: Long = System.currentTimeMillis(),
)
