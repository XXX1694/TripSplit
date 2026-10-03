package com.abzal.tripsplit.features.trips.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.trips.domain.model.TripDraft
import com.abzal.tripsplit.features.trips.presentation.components.TripFormFields

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
    AppPreview {
        CreateTripScreen(
            uiState = sampleCreateTripUiState,
            onDraftChange = {},
            onSaveClick = {},
            onBackClick = {},
            onPickCurrencyClick = {},
        )
    }
}
