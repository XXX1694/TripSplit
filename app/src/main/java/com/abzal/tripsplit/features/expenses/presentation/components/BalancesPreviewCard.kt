package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatSignedMoney
import com.abzal.tripsplit.features.expenses.presentation.TripOverviewUiState
import com.abzal.tripsplit.features.expenses.presentation.sampleTripOverviewUiState
import com.abzal.tripsplit.features.participants.presentation.components.tone
import kotlin.math.abs

/** Biggest balances of the trip. */
@Composable
fun BalancesPreviewCard(
    uiState: TripOverviewUiState,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val top = uiState.balances.sortedByDescending { abs(it.amount) }.take(3)
    AppCard(modifier = modifier) {
        SectionHeader(title = "Balances", actionText = "See all", onActionClick = onSeeAllClick)
        if (top.isEmpty()) EmptyHint("Add participants to see balances")
        top.forEach { balance ->
            val index = uiState.participants.indexOfFirst { it.id == balance.participantId }
            val name = uiState.participants.getOrNull(index)?.name.orEmpty()
            val isOwed = balance.amount >= 0
            AppListRow(
                title = if (isOwed) "$name gets back" else "$name owes",
                leading = { Avatar(initials = name.toInitials(), tone = avatarToneAt(index)) },
                trailing = {
                    Text(
                        text = formatSignedMoney(balance.amount, uiState.currency),
                        style = MaterialTheme.typography.titleSmall,
                        color = if (isOwed) AppTheme.colors.positive else AppTheme.colors.danger,
                    )
                },
            )
        }
    }
}

@ThemePreviews
@Composable
private fun BalancesPreviewCardPreview() {
    AppPreview {
        BalancesPreviewCard(
            uiState = sampleTripOverviewUiState,
            onSeeAllClick = {},
        )
    }
}
