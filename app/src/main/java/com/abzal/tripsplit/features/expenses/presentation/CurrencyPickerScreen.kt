package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.expenses.domain.model.CurrencyInfo
import com.abzal.tripsplit.features.expenses.presentation.components.CurrencyRow

@Composable
fun CurrencyPickerRoute(
    onBackClick: () -> Unit,
    onCurrencySelected: (String) -> Unit,
    viewModel: CurrencyPickerViewModel = injectedViewModel { c, _ -> CurrencyPickerViewModel(c.currencyRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CurrencyPickerScreen(
        uiState = uiState,
        onQueryChange = viewModel::onQueryChange,
        onCurrencyClick = viewModel::onCurrencySelect,
        onConfirmClick = { uiState.selectedCode?.let(onCurrencySelected) },
        onBackClick = onBackClick,
    )
}

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
        AppCard(verticalArrangement = Arrangement.spacedBy(0.dp)) {
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
    TripSplitTheme {
        CurrencyPickerScreen(
            uiState = CurrencyPickerUiState(
                currencies = listOf(
                    CurrencyInfo("EUR", "Euro", "€"),
                    CurrencyInfo("USD", "US Dollar", "$"),
                    CurrencyInfo("GBP", "British Pound", "£"),
                ),
                selectedCode = "USD",
            ),
            onQueryChange = {},
            onCurrencyClick = {},
            onConfirmClick = {},
            onBackClick = {},
        )
    }
}
