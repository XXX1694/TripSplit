package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.expenses.presentation.components.CurrencyRow
import com.abzal.tripsplit.features.participants.presentation.components.label

@Composable
fun CurrencyPickerScreen(
    uiState: CurrencyPickerUiState,
    onQueryChange: (String) -> Unit,
    onCurrencyClick: (code: String) -> Unit,
    onConfirmClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val selected = uiState.selectedCurrency

    AppScaffold(
        topBar = { AppTopBar(title = "Choose currency", onBackClick = onBackClick) },
        bottomBar = {
            BottomActionBar {
                PrimaryButton(
                    text = if (selected != null) "Use ${selected.name}" else "Choose a currency",
                    icon = Icons.Outlined.Check,
                    onClick = onConfirmClick,
                    enabled = selected != null,
                )
            }
        },
    ) {
        AppTextField(
            value = uiState.query,
            onValueChange = onQueryChange,
            label = "Search name or code",
            leadingIcon = Icons.Outlined.Search,
        )
        AppCard(verticalArrangement = Arrangement.spacedBy(Spacing.none)) {
            SectionHeader(title = "All currencies")
            uiState.visibleCurrencies.forEachIndexed { index, currency ->
                CurrencyRow(
                    currency = currency,
                    toneIndex = index,
                    isSelected = currency.code == uiState.selectedCode,
                    onClick = { onCurrencyClick(currency.code) },
                )
            }
            if (uiState.visibleCurrencies.isEmpty()) {
                Text(
                    text = "No currencies found",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.colors.textSecondary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrencyPickerScreenPreview() {
    AppPreview {
        CurrencyPickerScreen(
            uiState = sampleCurrencyPickerUiState,
            onQueryChange = {},
            onCurrencyClick = {},
            onConfirmClick = {},
            onBackClick = {},
        )
    }
}
