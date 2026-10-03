package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.components.AppDivider
import com.abzal.tripsplit.core.designsystem.components.ChevronIcon
import com.abzal.tripsplit.core.designsystem.components.RowValue
import com.abzal.tripsplit.core.preview.AppPreview

@Composable
fun LocalDataCard() {
    SettingsCard {
        SettingsRow(
            icon = Icons.Outlined.Storage,
            title = "Stored on this device",
            subtitle = "23.8 MB · Updated just now",
            trailing = { RowValue("Healthy") },
        )
        AppDivider()
        SettingsRow(
            icon = Icons.Outlined.Download,
            title = "Export all trip data",
            subtitle = "CSV and JSON archive",
            trailing = { ChevronIcon() },
        )
        AppDivider()
        SettingsRow(
            icon = Icons.Outlined.CloudOff,
            title = "Offline persistence",
            subtitle = "Available offline",
            trailing = { RowValue("On") },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LocalDataCardPreview() {
    AppPreview {
        LocalDataCard()
    }
}
