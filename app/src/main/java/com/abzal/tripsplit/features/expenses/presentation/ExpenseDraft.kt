package com.abzal.tripsplit.features.expenses.presentation

import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.expenses.domain.model.Expense

/** Values of the add / edit expense form. The amount is kept as text while the user types. */
data class ExpenseDraft(
    val title: String = "",
    val amountText: String = "",
    val currency: String = "EUR",
    val category: String = "Food",
    val paidById: String? = null,
    val participantIds: Set<String> = emptySet(),
) {
    // Accepts a decimal comma as well as a dot.
    val amount: Double? get() = amountText.replace(',', '.').toDoubleOrNull()

    val isValid: Boolean
        get() = title.isNotBlank() && (amount ?: 0.0) > 0.0 && paidById != null && participantIds.isNotEmpty()

    fun toggleParticipant(id: String): ExpenseDraft =
        copy(participantIds = if (id in participantIds) participantIds - id else participantIds + id)

    /** Builds an expense; [base] keeps id, trip and date when an existing expense is edited. */
    fun toExpense(tripId: String, base: Expense? = null): Expense {
        val values = Expense(
            tripId = tripId,
            title = title.trim(),
            amount = amount ?: 0.0,
            currency = currency,
            paidById = paidById.orEmpty(),
            participantIds = participantIds.toList(),
            category = category,
        )
        return if (base == null) values else values.copy(id = base.id, dateMillis = base.dateMillis)
    }
}

fun Expense.toDraft() = ExpenseDraft(
    title = title,
    amountText = amount.toString(),
    currency = currency,
    category = category,
    paidById = paidById,
    participantIds = participantIds.toSet(),
)
