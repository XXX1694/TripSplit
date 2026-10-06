package com.abzal.tripsplit.features.insights.presentation

import com.abzal.tripsplit.core.preview.sampleCategorySpending
import com.abzal.tripsplit.core.preview.sampleExpenses
import com.abzal.tripsplit.core.preview.sampleTrip

val sampleSpendingInsightsUiState = SpendingInsightsUiState(
    trip = sampleTrip,
    spending = sampleCategorySpending,
    expenses = sampleExpenses,
)
