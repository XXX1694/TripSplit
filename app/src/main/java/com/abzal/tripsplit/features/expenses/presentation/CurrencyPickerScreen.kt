package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.foundation.lazy.itemsIndexed
import com.abzal.tripsplit.core.designsystem.components.EmptyState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppLazyScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
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

    AppLazyScaffold(
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
        item {
            AppTextField(
                value = uiState.query,
                onValueChange = onQueryChange,
                label = "Search name or code",
                leadingIcon = Icons.Outlined.Search,
            )
        }
        item { SectionHeader(title = "All currencies") }
        if (uiState.visibleCurrencies.isEmpty()) {
            item {
                EmptyState(
                    title = "No currencies found",
                    message = "Check the name or the code and try again.",
                    icon = Icons.Outlined.Search,
                )
            }
        }
        itemsIndexed(uiState.visibleCurrencies, key = { _, currency -> currency.code }) { index, currency ->
            AppCard(onClick = { onCurrencyClick(currency.code) }) {
                CurrencyRow(
                    currency = currency,
                    toneIndex = index,
                    isSelected = currency.code == uiState.selectedCode,
                    onClick = { onCurrencyClick(currency.code) },
                )
            }
        }
    }
}

@ThemePreviews
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
