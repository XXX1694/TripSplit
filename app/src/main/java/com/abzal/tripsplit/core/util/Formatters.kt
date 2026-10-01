package com.abzal.tripsplit.core.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Currency
import java.util.Date
import java.util.Locale

/** 1284.6, "EUR" -> "€1,284.60" (depends on device locale). */
fun formatMoney(amount: Double, currencyCode: String): String {
    val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
    runCatching { format.currency = Currency.getInstance(currencyCode) }
    return format.format(amount)
}

/** "12–17 Sep" for one month, "28 Sep – 3 Oct" otherwise. */
fun formatTripDates(startMillis: Long?, endMillis: Long?): String {
    if (startMillis == null) return "No dates"
    val day = SimpleDateFormat("d", Locale.getDefault())
    val dayMonth = SimpleDateFormat("d MMM", Locale.getDefault())
    val start = Date(startMillis)
    if (endMillis == null) return dayMonth.format(start)

    val end = Date(endMillis)
    val startCalendar = Calendar.getInstance().apply { time = start }
    val endCalendar = Calendar.getInstance().apply { time = end }
    val sameMonth = startCalendar.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH) &&
        startCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR)
    return if (sameMonth) {
        "${day.format(start)}–${dayMonth.format(end)}"
    } else {
        "${dayMonth.format(start)} – ${dayMonth.format(end)}"
    }
}

/** "TUESDAY, 29 SEP" */
fun formatToday(): String =
    SimpleDateFormat("EEEE, d MMM", Locale.getDefault()).format(Date()).uppercase()

/** "12 Sep 2026" */
fun formatDate(millis: Long): String =
    SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(Date(millis))

fun currencyName(code: String): String =
    runCatching { Currency.getInstance(code).displayName }.getOrDefault(code)

fun currencySymbol(code: String): String =
    runCatching { Currency.getInstance(code).symbol }.getOrDefault(code)
