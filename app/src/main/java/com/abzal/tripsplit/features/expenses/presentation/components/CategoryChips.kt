package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppChip
import com.abzal.tripsplit.core.preview.AppPreview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryChips(selected: String, onSelect: (String) -> Unit) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(Spacing.xs), verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        expenseCategories.forEach { category ->
            AppChip(
                text = category,
                icon = categoryIcon(category),
                selected = category == selected,
                onClick = { onSelect(category) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryChipsPreview() {
    AppPreview {
        CategoryChips(
            selected = "Sample text",
            onSelect = {},
        )
    }
}
