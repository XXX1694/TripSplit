package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppChip
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews

@Composable
fun CategoryChips(selected: String, onSelect: (String) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.space8)) {
        items(expenseCategories) { category ->
            AppChip(
                text = category,
                icon = categoryIcon(category),
                selected = category == selected,
                onClick = { onSelect(category) },
            )
        }
    }
}

@ThemePreviews
@Composable
private fun CategoryChipsPreview() {
    AppPreview {
        CategoryChips(
            selected = "Sample text",
            onSelect = {},
        )
    }
}
