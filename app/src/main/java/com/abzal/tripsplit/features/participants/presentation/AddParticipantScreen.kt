package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.participants.presentation.components.TripHeaderCard
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun AddParticipantRoute(
    onBackClick: () -> Unit,
    onAdded: () -> Unit,
    viewModel: AddParticipantViewModel = injectedViewModel { c, h ->
        AddParticipantViewModel(h, c.tripRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AddParticipantScreen(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onAddClick = { viewModel.add(onAdded) },
        onBackClick = onBackClick,
    )
}

@Composable
fun AddParticipantScreen(
    uiState: AddParticipantUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    AppScaffold(
        topBar = { AppTopBar(title = "Add participant", subtitle = uiState.trip?.name, onBackClick = onBackClick) },
        bottomBar = {
            BottomActionBar {
                PrimaryButton(
                    text = "Add participant",
                    icon = Icons.Outlined.PersonAdd,
                    onClick = onAddClick,
                    enabled = uiState.isValid,
                    isLoading = uiState.isSaving,
                )
            }
        },
    ) {
        TripHeaderCard(trip = uiState.trip, caption = "Member access")
        AppCard {
            SectionHeader(title = "Add manually")
            AppTextField(
                value = uiState.name,
                onValueChange = onNameChange,
                label = "Full name",
                leadingIcon = Icons.Outlined.Person,
            )
            AppTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                label = "Email address",
                leadingIcon = Icons.Outlined.Email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            InfoBanner(text = "Member access · Can add expenses and view balances", icon = Icons.Outlined.VpnKey)
        }
        if (uiState.isValid) {
            InfoBanner(
                text = "${uiState.name.trim()} is ready to add as a member.",
                icon = Icons.Outlined.CheckCircle,
                tone = Tone.Primary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddParticipantScreenPreview() {
    TripSplitTheme {
        AddParticipantScreen(
            uiState = AddParticipantUiState(
                trip = Trip("1", "Lisbon Friends 2026", "EUR"),
                name = "Inês Silva",
                email = "ines.silva@gmail.com",
            ),
            onNameChange = {}, onEmailChange = {}, onAddClick = {}, onBackClick = {},
        )
    }
}
