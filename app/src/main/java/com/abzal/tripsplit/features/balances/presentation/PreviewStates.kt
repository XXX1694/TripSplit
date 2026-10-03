package com.abzal.tripsplit.features.balances.presentation

import com.abzal.tripsplit.core.preview.sampleBalances
import com.abzal.tripsplit.core.preview.sampleExpenses
import com.abzal.tripsplit.core.preview.sampleParticipants
import com.abzal.tripsplit.core.preview.sampleSettlements
import com.abzal.tripsplit.core.preview.sampleTransfers
import com.abzal.tripsplit.core.preview.sampleTrip

// Sample UI states for @Preview.

val sampleSettlementDraft = SettlementDraft(fromId = "p2", toId = "p1", amountText = "104.20")

val sampleBalancesUiState = BalancesUiState(
    trip = sampleTrip,
    balances = sampleBalances,
    settlements = sampleSettlements,
    transfers = sampleTransfers,
    expenses = sampleExpenses,
    participants = sampleParticipants,
)

val sampleOptimizedSettlementUiState = OptimizedSettlementUiState(
    trip = sampleTrip,
    transfers = sampleTransfers,
    participants = sampleParticipants,
)

val sampleRecordSettlementUiState = RecordSettlementUiState(
    trip = sampleTrip,
    participants = sampleParticipants,
    draft = sampleSettlementDraft,
)
