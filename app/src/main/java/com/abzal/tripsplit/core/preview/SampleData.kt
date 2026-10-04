package com.abzal.tripsplit.core.preview

import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.BarSegment
import com.abzal.tripsplit.core.designsystem.components.BarValue
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.features.autharization.domain.model.User
import com.abzal.tripsplit.features.balances.domain.model.Balance
import com.abzal.tripsplit.features.balances.domain.model.Settlement
import com.abzal.tripsplit.features.balances.domain.model.Transfer
import com.abzal.tripsplit.features.expenses.domain.model.CurrencyInfo
import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.insights.domain.model.CategorySpending
import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.domain.model.InvitationStatus
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.trips.domain.model.Trip

// Sample data for @Preview: plain data classes collected in lists.

private const val DAY = 86_400_000L

private val now = System.currentTimeMillis()

val sampleUser = User(id = "u1", name = "Maya Kim", email = "maya@hey.com")

val sampleTrip = Trip(
    id = "t1",
    name = "Lisbon Friends 2026",
    currency = "EUR",
    startDateMillis = now - 3 * DAY,
    endDateMillis = now + 2 * DAY,
    destination = "Lisbon, Portugal",
)

val samplePastTrip = Trip(
    id = "t2",
    name = "Kyoto Spring",
    currency = "JPY",
    startDateMillis = now - 200 * DAY,
    endDateMillis = now - 194 * DAY,
    destination = "Kyoto, Japan",
)

val sampleParticipants: List<Participant> = listOf(
    Participant("p1", "t1", "Maya Kim", "maya@hey.com"),
    Participant("p2", "t1", "Leo Evans", "leo.evans@gmail.com"),
    Participant("p3", "t1", "Sam Adeyemi", "sam.a@proton.me"),
    Participant("p4", "t1", "Nina Rossi", "nina@email.com"),
)

private val everyoneIds = sampleParticipants.map { it.id }

val sampleExpenses: List<Expense> = listOf(
    Expense("e1", "t1", "Dinner at Prado", 148.0, "EUR", "p1", everyoneIds, "Food", now),
    Expense("e2", "t1", "Sintra train tickets", 46.4, "EUR", "p3", everyoneIds, "Transit", now - 1 * DAY),
    Expense("e3", "t1", "Alfama apartment", 320.0, "EUR", "p1", everyoneIds, "Stay", now - 1 * DAY),
    Expense("e4", "t1", "Fado evening", 62.6, "EUR", "p4", everyoneIds, "Fun", now - 2 * DAY),
    Expense("e5", "t1", "Breakfast at Nicolau", 24.0, "EUR", "p2", everyoneIds.take(3), "Food", now - 2 * DAY),
)

val sampleBalances: List<Balance> = listOf(
    Balance("p1", 186.4),
    Balance("p2", -104.2),
    Balance("p3", 71.55),
    Balance("p4", -153.75),
)

val sampleTransfers: List<Transfer> = listOf(
    Transfer("p2", "p1", 104.2),
    Transfer("p4", "p1", 82.2),
    Transfer("p4", "p3", 71.55),
)

val sampleSettlements: List<Settlement> = listOf(
    Settlement("s1", "t1", "p2", "p1", 40.0, "EUR", now - DAY),
)

val sampleInvitations: List<Invitation> = listOf(
    Invitation("i1", "t1", "LIS26MAY", InvitationStatus.ACCEPTED),
    Invitation("i2", "t1", "KQ4PX7ZD", InvitationStatus.PENDING),
    Invitation("i3", "t1", "OLD00001", InvitationStatus.REVOKED),
)

val sampleCurrencies: List<CurrencyInfo> = listOf(
    CurrencyInfo("EUR", "Euro", "€"),
    CurrencyInfo("USD", "US Dollar", "$"),
    CurrencyInfo("GBP", "British Pound", "£"),
    CurrencyInfo("JPY", "Japanese Yen", "¥"),
)

val sampleCategorySpending: List<CategorySpending> = listOf(
    CategorySpending("Stay", 534.0),
    CategorySpending("Food", 377.8),
    CategorySpending("Transit", 220.6),
    CategorySpending("Fun", 152.2),
)

@Composable
fun sampleBarSegments(): List<BarSegment> = listOf(
    BarSegment(534f, AppTheme.colors.accent),
    BarSegment(378f, AppTheme.colors.warning),
    BarSegment(221f, AppTheme.colors.info),
)

fun sampleBarValues(): List<BarValue> = listOf(
    BarValue("12", 120.0),
    BarValue("13", 210.0),
    BarValue("14", 150.0),
    BarValue("15", 308.0, isHighlighted = true),
    BarValue("16", 190.0),
)
