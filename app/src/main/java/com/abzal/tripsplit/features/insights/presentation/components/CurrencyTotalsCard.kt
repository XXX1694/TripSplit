package com.abzal.tripsplit.features.insights.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.insights.presentation.SpendingInsightsUiState
import com.abzal.tripsplit.features.insights.presentation.sampleSpendingInsightsUiState

/** Totals per currency, shown only when expenses use more than one currency. */
@Composable
fun CurrencyTotalsCard(uiState: SpendingInsightsUiState, modifier: Modifier = Modifier) {
    AppCard(modifier = modifier) {
        SectionHeader(title = "Spending by currency")
        uiState.currencyTotals.forEach { (code, total) ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(code, style = MaterialTheme.typography.bodyMedium, color = AppTheme.colors.text)
                Text(formatMoney(total, code), style = MaterialTheme.typography.titleSmall, color = AppTheme.colors.text)
            }
        }
    }
}

@ThemePreviews
@Composable
private fun CurrencyTotalsCardPreview() {
    AppPreview {
        CurrencyTotalsCard(
            uiState = sampleSpendingInsightsUiState,
        )
    }
}
