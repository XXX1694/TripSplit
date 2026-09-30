package com.abzal.tripsplit.core.di

import com.abzal.tripsplit.features.autharization.data.repository.AuthRepositoryImpl
import com.abzal.tripsplit.features.autharization.domain.repository.AuthRepository
import com.abzal.tripsplit.features.balances.data.repository.BalanceRepositoryImpl
import com.abzal.tripsplit.features.balances.domain.repository.BalanceRepository
import com.abzal.tripsplit.features.expenses.data.repository.CurrencyRepositoryImpl
import com.abzal.tripsplit.features.expenses.data.repository.ExpenseRepositoryImpl
import com.abzal.tripsplit.features.expenses.domain.repository.CurrencyRepository
import com.abzal.tripsplit.features.expenses.domain.repository.ExpenseRepository
import com.abzal.tripsplit.features.insights.data.repository.InsightsRepositoryImpl
import com.abzal.tripsplit.features.insights.domain.repository.InsightsRepository
import com.abzal.tripsplit.features.participants.data.repository.ParticipantRepositoryImpl
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import com.abzal.tripsplit.features.trips.data.repository.TripRepositoryImpl
import com.abzal.tripsplit.features.trips.domain.repository.TripRepository

/** Manual dependency container; one instance lives in [com.abzal.tripsplit.TripSplitApp]. */
class AppContainer {
    val authRepository: AuthRepository by lazy { AuthRepositoryImpl() }
    val tripRepository: TripRepository by lazy { TripRepositoryImpl() }
    val expenseRepository: ExpenseRepository by lazy { ExpenseRepositoryImpl() }
    val currencyRepository: CurrencyRepository by lazy { CurrencyRepositoryImpl() }
    val participantRepository: ParticipantRepository by lazy { ParticipantRepositoryImpl() }
    val balanceRepository: BalanceRepository by lazy {
        BalanceRepositoryImpl(expenseRepository, participantRepository)
    }
    val insightsRepository: InsightsRepository by lazy { InsightsRepositoryImpl(expenseRepository) }
}
