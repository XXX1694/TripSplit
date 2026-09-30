package com.abzal.tripsplit.features.insights.domain.repository

import com.abzal.tripsplit.features.insights.domain.model.CategorySpending
import kotlinx.coroutines.flow.Flow

interface InsightsRepository {
    fun observeSpendingByCategory(tripId: String): Flow<List<CategorySpending>>
}
