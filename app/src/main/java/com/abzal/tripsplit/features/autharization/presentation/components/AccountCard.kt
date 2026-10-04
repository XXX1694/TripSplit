package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.designsystem.components.AppDivider
import com.abzal.tripsplit.core.designsystem.components.ChevronIcon
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.presentation.components.tone

@Composable
fun AccountCard(onSignOutClick: () -> Unit, onDeleteAccountClick: () -> Unit) {
    SettingsCard {
        SettingsRow(
            icon = Icons.AutoMirrored.Outlined.Logout,
            title = "Sign out",
            subtitle = "Keep local data on this device",
            trailing = { ChevronIcon() },
            onClick = onSignOutClick,
        )
        AppDivider()
        SettingsRow(
            icon = Icons.Outlined.PersonRemove,
            title = "Delete account",
            subtitle = "Permanently remove profile and local data",
            tone = Tone.Danger,
            trailing = { ChevronIcon() },
            onClick = onDeleteAccountClick,
        )
    }
}

@ThemePreviews
@Composable
private fun AccountCardPreview() {
    AppPreview {
        AccountCard(
            onSignOutClick = {},
            onDeleteAccountClick = {},
        )
    }
}
