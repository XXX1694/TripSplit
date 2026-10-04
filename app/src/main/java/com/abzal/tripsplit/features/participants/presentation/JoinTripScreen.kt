package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.presentation.components.JoinTripHeader
import com.abzal.tripsplit.features.participants.presentation.components.label
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun JoinTripScreen(
    uiState: JoinTripUiState,
    onJoinClick: (code: String) -> Unit,
    onBackClick: () -> Unit,
) {
    var code by remember { mutableStateOf("") }

    AppScaffold(
        topBar = { AppTopBar(title = "Trip invitation", onBackClick = onBackClick) },
        bottomBar = {
            BottomActionBar {
                PrimaryButton(
                    text = "Accept & join trip",
                    icon = Icons.Outlined.Check,
                    onClick = { onJoinClick(code) },
                    enabled = code.isNotBlank(),
                    isLoading = uiState.isLoading,
                )
                AppTextButton(text = "Decline invitation", onClick = onBackClick, modifier = Modifier.fillMaxWidth())
            }
        },
    ) {
        JoinTripHeader()
        AppTextField(
            value = code,
            onValueChange = { code = it.uppercase() },
            label = "Invitation code",
            leadingIcon = Icons.Outlined.VpnKey,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
            isError = uiState.error != null,
            supportingText = uiState.error,
        )
        InfoBanner(
            text = "Your role is Member. You can add expenses and view balances, but cannot manage the trip.",
            icon = Icons.Outlined.VpnKey,
        )
    }
}

@ThemePreviews
@Composable
private fun JoinTripScreenPreview() {
    AppPreview {
        JoinTripScreen(
            uiState = sampleJoinTripUiState,
            onJoinClick = {},
            onBackClick = {},
        )
    }
}
