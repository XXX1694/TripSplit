package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.DirectionsTransit
import androidx.compose.material.icons.outlined.Hotel
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector
import com.abzal.tripsplit.core.designsystem.components.Tone

/** Categories offered when adding an expense. */
val expenseCategories = listOf("Food", "Stay", "Transit", "Fun", "Other")

fun categoryIcon(category: String): ImageVector = when (category) {
    "Food" -> Icons.Outlined.Restaurant
    "Stay" -> Icons.Outlined.Hotel
    "Transit" -> Icons.Outlined.DirectionsTransit
    "Fun" -> Icons.Outlined.Celebration
    else -> Icons.Outlined.Category
}

fun categoryTone(category: String): Tone = when (category) {
    "Food" -> Tone.Warning
    "Stay" -> Tone.Accent
    "Transit" -> Tone.Info
    "Fun" -> Tone.Danger
    else -> Tone.Accent
}
