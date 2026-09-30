package com.abzal.tripsplit.features.expenses.domain.repository

import com.abzal.tripsplit.features.expenses.domain.model.CurrencyInfo

interface CurrencyRepository {
    fun getCurrencies(): List<CurrencyInfo>
}
