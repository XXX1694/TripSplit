package com.abzal.tripsplit.features.expenses.data.repository

import com.abzal.tripsplit.features.expenses.domain.model.CurrencyInfo
import com.abzal.tripsplit.features.expenses.domain.repository.CurrencyRepository

class CurrencyRepositoryImpl : CurrencyRepository {
    private val currencies = listOf(
        CurrencyInfo("USD", "US Dollar", "$"),
        CurrencyInfo("EUR", "Euro", "€"),
        CurrencyInfo("GBP", "British Pound", "£"),
        CurrencyInfo("KZT", "Kazakhstani Tenge", "₸"),
        CurrencyInfo("RUB", "Russian Ruble", "₽"),
        CurrencyInfo("TRY", "Turkish Lira", "₺"),
        CurrencyInfo("JPY", "Japanese Yen", "¥"),
        CurrencyInfo("CHF", "Swiss Franc", "CHF"),
        CurrencyInfo("CAD", "Canadian Dollar", "CA$"),
        CurrencyInfo("AUD", "Australian Dollar", "A$"),
        CurrencyInfo("CNY", "Chinese Yuan", "CN¥"),
        CurrencyInfo("AED", "UAE Dirham", "AED"),
    )

    override fun getCurrencies(): List<CurrencyInfo> = currencies
}
