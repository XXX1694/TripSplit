package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.captionMedium
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.expenses.presentation.ExpenseDayGroup
import com.abzal.tripsplit.features.expenses.presentation.sampleExpenseDayGroup

/** "TODAY · 15 SEP" on the left and the total of that day on the right. */
@Composable
fun ExpenseDayHeader(
    group: ExpenseDayGroup,
    currency: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(group.title, style = MaterialTheme.typography.labelSmall, color = AppTheme.colors.textMuted)
        Text(
            text = formatMoney(group.total, currency),
            style = MaterialTheme.typography.captionMedium,
            color = AppTheme.colors.textMuted,
        )
    }
}

@ThemePreviews
@Composable
private fun ExpenseDayHeaderPreview() {
    AppPreview {
        ExpenseDayHeader(group = sampleExpenseDayGroup, currency = "EUR")
    }
}
