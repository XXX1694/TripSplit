package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.preview.AppPreview

/** Card without inner gaps: rows are separated by dividers. */
@Composable
fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    AppCard(verticalArrangement = Arrangement.spacedBy(Spacing.none), content = content)
}

@Preview(showBackground = true)
@Composable
private fun SettingsCardPreview() {
    AppPreview {
        SettingsCard(
            content = { Text("Content") },
        )
    }
}
