package com.abzal.tripsplit.features.balances.data.repository

import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.balances.domain.model.Balance
import com.abzal.tripsplit.features.balances.domain.model.Settlement
import com.abzal.tripsplit.features.balances.domain.model.Transfer
import com.abzal.tripsplit.features.balances.domain.repository.BalanceRepository
import com.abzal.tripsplit.features.expenses.domain.repository.ExpenseRepository
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import kotlin.math.abs
import kotlin.math.min
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class BalanceRepositoryImpl(
    private val expenseRepository: ExpenseRepository,
    private val participantRepository: ParticipantRepository,
) : BalanceRepository {
    private val settlements = MutableStateFlow<List<Settlement>>(emptyList())

    override fun observeSettlements(tripId: String): Flow<List<Settlement>> =
        settlements.map { list -> list.filter { it.tripId == tripId } }

    override fun observeBalances(tripId: String): Flow<List<Balance>> = combine(
        expenseRepository.observeExpenses(tripId),
        participantRepository.observeParticipants(tripId),
        observeSettlements(tripId),
    ) { expenses, participants, tripSettlements ->
        // Balance = paid - own share (equal split) + payments sent - payments received.
        // Positive: the person gets money back, negative: the person owes.
        val totals = participants.associate { it.id to 0.0 }.toMutableMap()
        expenses.forEach { expense ->
            totals[expense.paidById] = (totals[expense.paidById] ?: 0.0) + expense.amount
            val share = expense.amount / expense.participantIds.size.coerceAtLeast(1)
            expense.participantIds.forEach { id -> totals[id] = (totals[id] ?: 0.0) - share }
        }
        tripSettlements.forEach { s ->
            totals[s.fromId] = (totals[s.fromId] ?: 0.0) + s.amount
            totals[s.toId] = (totals[s.toId] ?: 0.0) - s.amount
        }
        totals.map { (id, amount) -> Balance(id, amount) }
    }

    override fun observeOptimizedTransfers(tripId: String): Flow<List<Transfer>> =
        observeBalances(tripId).map { balances ->
            // Greedy settlement: match the biggest-first debtors with creditors until everything is covered.
            val debtors = balances.filter { it.amount < -EPS }.map { it.participantId to -it.amount }.toMutableList()
            val creditors = balances.filter { it.amount > EPS }.map { it.participantId to it.amount }.toMutableList()
            val transfers = mutableListOf<Transfer>()
            var d = 0
            var c = 0
            while (d < debtors.size && c < creditors.size) {
                val pay = min(debtors[d].second, creditors[c].second)
                transfers += Transfer(debtors[d].first, creditors[c].first, pay)
                debtors[d] = debtors[d].first to debtors[d].second - pay
                creditors[c] = creditors[c].first to creditors[c].second - pay
                if (abs(debtors[d].second) < EPS) d++
                if (abs(creditors[c].second) < EPS) c++
            }
            transfers
        }

    override suspend fun recordSettlement(settlement: Settlement) {
        settlements.update { it + settlement }
    }

    private companion object {
        const val EPS = 0.005
    }
}
