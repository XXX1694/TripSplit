package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.FlightTakeoff
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BigIconBadge
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.StatusPill
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun JoinTripRoute(
    onBackClick: () -> Unit,
    onJoined: (String) -> Unit,
    viewModel: JoinTripViewModel = injectedViewModel { c, _ -> JoinTripViewModel(c.participantRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    JoinTripScreen(
        uiState = uiState,
        onJoinClick = { code -> viewModel.join(code, onJoined) },
        onBackClick = onBackClick,
    )
}

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

@Composable
private fun JoinTripHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        BigIconBadge(icon = Icons.Outlined.FlightTakeoff)
        StatusPill(text = "YOU'RE INVITED")
        Text(
            text = "Join a trip",
            style = MaterialTheme.typography.headlineLarge,
            color = AppTheme.colors.textPrimary,
        )
        Text(
            text = "Enter the invitation code you received to share expenses and settle the trip together.",
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JoinTripScreenPreview() {
    TripSplitTheme {
        JoinTripScreen(uiState = JoinTripUiState(), onJoinClick = {}, onBackClick = {})
    }
}
