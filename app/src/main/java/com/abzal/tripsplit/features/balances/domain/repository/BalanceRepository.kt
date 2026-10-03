package com.abzal.tripsplit.features.balances.domain.repository

import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.balances.domain.model.Balance
import com.abzal.tripsplit.features.balances.domain.model.Settlement
import com.abzal.tripsplit.features.balances.domain.model.Transfer
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
    fun observeBalances(tripId: String): Flow<List<Balance>>
    fun observeSettlements(tripId: String): Flow<List<Settlement>>
    fun observeOptimizedTransfers(tripId: String): Flow<List<Transfer>>
    suspend fun recordSettlement(settlement: Settlement)
}
