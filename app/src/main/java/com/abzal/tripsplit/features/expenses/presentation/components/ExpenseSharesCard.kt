package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.expenses.presentation.ExpenseDetailUiState
import com.abzal.tripsplit.features.expenses.presentation.sampleExpenseDetailUiState
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.participants.presentation.components.tone

/** Who paid and how the amount is split between participants. */
@Composable
fun ExpenseSharesCard(uiState: ExpenseDetailUiState, modifier: Modifier = Modifier) {
    val expense = uiState.expense ?: return
    val payerName = uiState.payer?.name.orEmpty()
    val sharing = uiState.sharingParticipants
    val percent = if (sharing.isEmpty()) 0 else 100 / sharing.size

    AppCard(modifier = modifier) {
        AppListRow(
            title = payerName,
            leading = { Avatar(initials = payerName.toInitials(), size = Sizes.avatarMedium) },
            trailing = {
                Text(
                    text = "Paid ${formatMoney(expense.amount, expense.currency)}",
                    style = MaterialTheme.typography.labelLarge,
                    color = AppTheme.colors.positive,
                )
            },
        )
        HorizontalDivider(color = AppTheme.colors.divider)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Participant shares", style = MaterialTheme.typography.titleMedium, color = AppTheme.colors.textPrimary)
            Text("Equal split", style = MaterialTheme.typography.labelMedium, color = AppTheme.colors.positive)
        }
        sharing.forEachIndexed { index, participant ->
            AppListRow(
                title = participant.name,
                leading = { Avatar(initials = participant.name.toInitials(), tone = avatarToneAt(index)) },
                trailing = {
                    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.md), verticalAlignment = Alignment.CenterVertically) {
                        Text("$percent%", style = MaterialTheme.typography.bodySmall, color = AppTheme.colors.textSecondary)
                        Text(
                            text = formatMoney(uiState.sharePerPerson, expense.currency),
                            style = MaterialTheme.typography.titleSmall,
                            color = AppTheme.colors.textPrimary,
                        )
                    }
                },
            )
        }
    }
}

@ThemePreviews
@Composable
private fun ExpenseSharesCardPreview() {
    AppPreview {
        ExpenseSharesCard(
            uiState = sampleExpenseDetailUiState,
        )
    }
}
