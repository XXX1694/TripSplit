package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.preview.sampleExpenses
import com.abzal.tripsplit.core.util.formatDateTime
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.expenses.domain.model.Expense

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
                modifier = Modifier.size(Sizes.iconBadgeLarge).clip(MaterialTheme.shapes.medium).background(AppTheme.colors.heroOverlay),
                contentAlignment = Alignment.Center,
            ) {
                Icon(categoryIcon(expense.category), contentDescription = null)
            }
            Text(formatMoney(expense.amount, expense.currency), style = MaterialTheme.typography.headlineLarge)
        }
        HorizontalDivider(color = AppTheme.colors.heroOutline)
        Column {
            Text(expense.title, style = MaterialTheme.typography.titleLarge)
            Text(
                text = "${expense.category} · ${formatDateTime(expense.dateMillis)}",
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.colors.onHeroMuted,
            )
        }
    }
}

@ThemePreviews
@Composable
private fun ExpenseSummaryCardPreview() {
    AppPreview {
        ExpenseSummaryCard(
            expense = sampleExpenses.first(),
        )
    }
}
