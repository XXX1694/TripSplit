package com.abzal.tripsplit.features.trips.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Balance
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppDivider
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.CoverImage
import com.abzal.tripsplit.core.designsystem.components.IconBadge
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.core.util.formatTripDates
import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.participants.presentation.components.tone
import com.abzal.tripsplit.features.trips.presentation.DeleteTripUiState
import com.abzal.tripsplit.features.trips.presentation.sampleDeleteTripUiState

/** Shows what will be lost: the trip and counts of its expenses, participants and open balances. */
@Composable
fun DeleteTripSummaryCard(uiState: DeleteTripUiState, modifier: Modifier = Modifier) {
    val trip = uiState.trip ?: return

    AppCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            CoverImage(modifier = Modifier.size(Sizes.coverThumb).clip(MaterialTheme.shapes.medium))
            Column {
                Text(text = trip.name, style = MaterialTheme.typography.titleMedium, color = AppTheme.colors.textPrimary)
                Text(
                    text = "${formatTripDates(trip.startDateMillis, trip.endDateMillis)} · ${trip.currency}",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.colors.textSecondary,
                )
            }
        }
        AppDivider()
        LossRow(Icons.Outlined.Receipt, "${uiState.expenseCount} expenses", "Expense history and saved exchange rates")
        LossRow(
            Icons.Outlined.Groups,
            "${uiState.participantNames.size} participants",
            "Access for ${uiState.participantNames.joinToString(", ")}",
        )
        LossRow(
            Icons.Outlined.Balance,
            "${formatMoney(uiState.unsettledAmount, trip.currency)} unsettled",
            "Open balances and the payment plan",
        )
    }
}

@Composable
private fun LossRow(icon: ImageVector, title: String, subtitle: String) {
    AppListRow(
        title = title,
        subtitle = subtitle,
        leading = { IconBadge(icon = icon, tone = Tone.Negative) },
    )
}

@Preview(showBackground = true)
@Composable
private fun DeleteTripSummaryCardPreview() {
    AppPreview {
        DeleteTripSummaryCard(
            uiState = sampleDeleteTripUiState,
        )
    }
}
