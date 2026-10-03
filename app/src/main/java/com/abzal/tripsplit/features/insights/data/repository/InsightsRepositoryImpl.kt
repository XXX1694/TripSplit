package com.abzal.tripsplit.features.insights.data.repository

import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.expenses.domain.repository.ExpenseRepository
import com.abzal.tripsplit.features.insights.domain.model.CategorySpending
import com.abzal.tripsplit.features.insights.domain.repository.InsightsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InsightsRepositoryImpl(
    private val expenseRepository: ExpenseRepository,
) : InsightsRepository {
    override fun observeSpendingByCategory(tripId: String): Flow<List<CategorySpending>> =
        expenseRepository.observeExpenses(tripId).map { expenses ->
            expenses.groupBy { it.category }
                .map { (category, items) -> CategorySpending(category, items.sumOf { it.amount }) }
                .sortedByDescending { it.total }
        }
}
