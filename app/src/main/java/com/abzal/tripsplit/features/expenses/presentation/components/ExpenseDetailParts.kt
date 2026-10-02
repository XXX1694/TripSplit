package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.DangerButton
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.SecondaryButton
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.util.formatDateTime
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.expenses.presentation.ExpenseDetailUiState

private val mutedWhite = Color.White.copy(alpha = 0.8f)

/** Dark card: category icon, amount, title and date. */
@Composable
fun ExpenseSummaryCard(expense: Expense, modifier: Modifier = Modifier) {
    HeroCard(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.size(48.dp).clip(MaterialTheme.shapes.medium).background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(categoryIcon(expense.category), contentDescription = null)
            }
            Text(formatMoney(expense.amount, expense.currency), style = MaterialTheme.typography.headlineLarge)
        }
        HorizontalDivider(color = Color.White.copy(alpha = 0.3f))
        Column {
            Text(expense.title, style = MaterialTheme.typography.titleLarge)
            Text(
                text = "${expense.category} · ${formatDateTime(expense.dateMillis)}",
                style = MaterialTheme.typography.bodySmall,
                color = mutedWhite,
            )
        }
    }
}

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
            leading = { Avatar(initials = payerName.toInitials(), size = 48.dp) },
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

/** Red box that asks to confirm the deletion. */
@Composable
fun DeleteExpenseConfirmation(
    expenseTitle: String,
    participantCount: Int,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = colors.negativeContainer,
        border = BorderStroke(1.dp, colors.negative),
    ) {
        Column(modifier = Modifier.padding(Spacing.md), verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                Icon(Icons.Outlined.WarningAmber, contentDescription = null, tint = colors.negative)
                Text("Delete $expenseTitle?", style = MaterialTheme.typography.titleMedium, color = colors.negative)
            }
            Text(
                text = "All $participantCount balances will be recalculated. This action cannot be undone.",
                style = MaterialTheme.typography.bodySmall,
                color = colors.negative,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                SecondaryButton(text = "Cancel", onClick = onCancelClick, modifier = Modifier.weight(1f))
                DangerButton(text = "Delete expense", icon = Icons.Outlined.Delete, onClick = onDeleteClick, modifier = Modifier.weight(1f))
            }
        }
    }
}
