package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.Overline
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.autharization.presentation.components.AccountCard
import com.abzal.tripsplit.features.autharization.presentation.components.AppVersionText
import com.abzal.tripsplit.features.autharization.presentation.components.LocalDataCard
import com.abzal.tripsplit.features.autharization.presentation.components.PreferencesCard
import com.abzal.tripsplit.features.autharization.presentation.components.ProfileHeader
import com.abzal.tripsplit.features.participants.presentation.components.tone

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onSignOutClick: () -> Unit,
    onClearLocalDataClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    AppScaffold(
        topBar = { AppTopBar(title = "Profile & settings", onBackClick = onBackClick) },
    ) {
        ProfileHeader(user = uiState.user)

        Overline("Preferences")
        PreferencesCard()

        Overline("Local data")
        LocalDataCard()

        InfoBanner(
            text = "Your local data is encrypted. Changes sync automatically when you're online.",
            icon = Icons.Outlined.Shield,
            tone = Tone.Primary,
        )
        AccountCard(onSignOutClick = onSignOutClick, onDeleteAccountClick = onClearLocalDataClick)
        AppVersionText()
    }
}

@ThemePreviews
@Composable
private fun ProfileScreenPreview() {
    AppPreview {
        ProfileScreen(
            uiState = sampleProfileUiState,
            onSignOutClick = {},
            onClearLocalDataClick = {},
            onBackClick = {},
        )
    }
}
