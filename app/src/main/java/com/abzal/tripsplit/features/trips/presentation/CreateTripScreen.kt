package com.abzal.tripsplit.features.trips.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.trips.domain.model.TripDraft
import com.abzal.tripsplit.features.trips.presentation.components.TripFormFields

@Composable
fun CreateTripRoute(
    onBackClick: () -> Unit,
    onCreated: (String) -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
    viewModel: CreateTripViewModel = injectedViewModel { c, _ -> CreateTripViewModel(c.tripRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(pickedCurrency) {
        if (pickedCurrency != null) viewModel.onCurrencyPicked(pickedCurrency)
    }

    CreateTripScreen(
        uiState = uiState,
        onDraftChange = viewModel::onDraftChange,
        onSaveClick = { viewModel.createTrip(onCreated) },
        onBackClick = onBackClick,
        onPickCurrencyClick = onPickCurrencyClick,
    )
}

@Composable
fun CreateTripScreen(
    uiState: CreateTripUiState,
    onDraftChange: (TripDraft) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    onPickCurrencyClick: () -> Unit,
) {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Create trip",
                onBackClick = onBackClick,
                actions = {
                    AppTextButton(text = "Create", onClick = onSaveClick, enabled = uiState.draft.isValid)
                },
            )
        },
        bottomBar = {
            BottomActionBar {
                PrimaryButton(
                    text = "Create trip",
                    icon = Icons.Outlined.Add,
                    onClick = onSaveClick,
                    enabled = uiState.draft.isValid,
                    isLoading = uiState.isSaving,
                )
            }
        },
    ) {
        TripFormFields(
            draft = uiState.draft,
            onDraftChange = onDraftChange,
            onPickCurrencyClick = onPickCurrencyClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateTripScreenPreview() {
    TripSplitTheme {
        CreateTripScreen(
            uiState = CreateTripUiState(TripDraft(name = "Lisbon Friends 2026", destination = "Lisbon, Portugal")),
            onDraftChange = {},
            onSaveClick = {},
            onBackClick = {},
            onPickCurrencyClick = {},
        )
    }
}
