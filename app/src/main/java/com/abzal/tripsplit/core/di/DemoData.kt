package com.abzal.tripsplit.core.di

import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.domain.model.InvitationStatus
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.trips.domain.model.Trip

/** Demo content the in-memory repositories start with, so every list screen has data. */
object DemoData {
    private const val DAY = 86_400_000L
    private val now = System.currentTimeMillis()

    private fun trip(id: String, name: String, destination: String, currency: String, startDaysAgo: Int, lengthDays: Int) = Trip(
        id = id,
        name = name,
        currency = currency,
        startDateMillis = now - startDaysAgo * DAY,
        endDateMillis = now - startDaysAgo * DAY + lengthDays * DAY,
        destination = destination,
    )

    val trips: List<Trip> = listOf(
        trip("lisbon", "Lisbon Friends 2026", "Lisbon, Portugal", "EUR", startDaysAgo = 6, lengthDays = 8),
        trip("kyoto", "Kyoto Spring", "Kyoto, Japan", "JPY", startDaysAgo = 200, lengthDays = 6),
        trip("alps", "Alpine Weekend", "Innsbruck, Austria", "EUR", startDaysAgo = 260, lengthDays = 2),
        trip("berlin", "Berlin Weekend", "Berlin, Germany", "EUR", startDaysAgo = 300, lengthDays = 3),
        trip("istanbul", "Istanbul Getaway", "Istanbul, Turkey", "TRY", startDaysAgo = 340, lengthDays = 5),
        trip("prague", "Prague Autumn", "Prague, Czechia", "EUR", startDaysAgo = 380, lengthDays = 4),
        trip("barcelona", "Barcelona Tapas", "Barcelona, Spain", "EUR", startDaysAgo = 420, lengthDays = 5),
        trip("iceland", "Iceland Road Trip", "Reykjavik, Iceland", "USD", startDaysAgo = 470, lengthDays = 9),
        trip("almaty", "Almaty Mountains", "Almaty, Kazakhstan", "KZT", startDaysAgo = 520, lengthDays = 4),
        trip("rome", "Rome Escape", "Rome, Italy", "EUR", startDaysAgo = 560, lengthDays = 4),
        trip("vienna", "Vienna Christmas", "Vienna, Austria", "EUR", startDaysAgo = 600, lengthDays = 3),
        trip("london", "London Long Weekend", "London, UK", "GBP", startDaysAgo = 650, lengthDays = 4),
    )

    private val lisbonPeople = listOf(
        Participant("lisbon-maya", "lisbon", "Maya Kim", "maya@hey.com"),
        Participant("lisbon-leo", "lisbon", "Leo Evans", "leo.evans@gmail.com"),
        Participant("lisbon-sam", "lisbon", "Sam Adeyemi", "sam.a@proton.me"),
        Participant("lisbon-nina", "lisbon", "Nina Rossi", "nina@email.com"),
    )

    /** Everyone except the first trip has a small crew: Maya and Leo, plus Sam on every other trip. */
    private val otherPeople: List<Participant> = trips.drop(1).flatMapIndexed { index, trip ->
        buildList {
            add(Participant("${trip.id}-maya", trip.id, "Maya Kim", "maya@hey.com"))
            add(Participant("${trip.id}-leo", trip.id, "Leo Evans", "leo.evans@gmail.com"))
            if (index % 2 == 0) add(Participant("${trip.id}-sam", trip.id, "Sam Adeyemi", "sam.a@proton.me"))
        }
    }

    val participants: List<Participant> = lisbonPeople + otherPeople

    private val lisbonIds = lisbonPeople.map { it.id }

    private fun lisbon(id: String, title: String, amount: Double, category: String, paidBy: Int, daysAgo: Int, people: List<String> = lisbonIds) =
        Expense(id, "lisbon", title, amount, "EUR", lisbonIds[paidBy], people, category, now - daysAgo * DAY - paidBy * 3_600_000L)

    private val lisbonExpenses = listOf(
        lisbon("l1", "Alfama apartment", 640.0, "Stay", 0, 6),
        lisbon("l2", "Airport taxi", 38.5, "Transit", 1, 6),
        lisbon("l3", "Welcome dinner", 126.4, "Food", 2, 5),
        lisbon("l4", "Tram 28 tickets", 24.0, "Transit", 3, 5),
        lisbon("l5", "Pastéis & coffee", 24.6, "Food", 1, 4),
        lisbon("l6", "Sintra train tickets", 46.4, "Transit", 2, 4),
        lisbon("l7", "Pena Palace entry", 60.0, "Fun", 0, 4),
        lisbon("l8", "Lunch in Belém", 88.2, "Food", 3, 3),
        lisbon("l9", "Fado evening", 62.6, "Fun", 3, 3),
        lisbon("l10", "Groceries", 41.9, "Food", 1, 2, lisbonIds.take(3)),
        lisbon("l11", "Boat tour", 120.0, "Fun", 2, 2),
        lisbon("l12", "Dinner at Prado", 148.0, "Food", 0, 1),
        lisbon("l13", "Metro cards", 19.2, "Transit", 1, 1),
        lisbon("l14", "Breakfast at Nicolau", 24.0, "Food", 2, 0, lisbonIds.take(3)),
    )

    private val kyotoExpenses = listOf(
        Expense("k1", "kyoto", "Ryokan stay", 96_000.0, "JPY", "kyoto-maya", listOf("kyoto-maya", "kyoto-leo"), "Stay", now - 198 * DAY),
        Expense("k2", "kyoto", "Kaiseki dinner", 28_400.0, "JPY", "kyoto-leo", listOf("kyoto-maya", "kyoto-leo"), "Food", now - 197 * DAY),
        Expense("k3", "kyoto", "Shinkansen tickets", 27_200.0, "JPY", "kyoto-maya", listOf("kyoto-maya", "kyoto-leo"), "Transit", now - 196 * DAY),
    )

    val expenses: List<Expense> = lisbonExpenses + kyotoExpenses

    val invitations: List<Invitation> = listOf(
        Invitation("i1", "lisbon", "LIS26MAY", InvitationStatus.ACCEPTED),
        Invitation("i2", "lisbon", "KQ4PX7ZD", InvitationStatus.PENDING),
        Invitation("i3", "lisbon", "OLD00001", InvitationStatus.REVOKED),
    )
}
