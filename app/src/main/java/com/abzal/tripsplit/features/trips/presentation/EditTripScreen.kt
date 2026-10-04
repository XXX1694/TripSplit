package com.abzal.tripsplit.features.trips.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.trips.domain.model.TripDraft
import com.abzal.tripsplit.features.trips.presentation.components.TripFormFields

@Composable
fun EditTripScreen(
    uiState: EditTripUiState,
    onDraftChange: (TripDraft) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    onPickCurrencyClick: () -> Unit,
) {
    val canSave = uiState.isLoaded && uiState.draft.isValid

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Edit trip",
                onBackClick = onBackClick,
                actions = { AppTextButton(text = "Save", onClick = onSaveClick, enabled = canSave) },
            )
        },
        bottomBar = {
            BottomActionBar {
                PrimaryButton(
                    text = "Save changes",
                    icon = Icons.Outlined.Check,
                    onClick = onSaveClick,
                    enabled = canSave,
                    isLoading = uiState.isSaving,
                )
                AppTextButton(
                    text = "Delete trip",
                    icon = Icons.Outlined.Delete,
                    onClick = onDeleteClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
    ) {
        TripFormFields(
            draft = uiState.draft,
            onDraftChange = onDraftChange,
            onPickCurrencyClick = onPickCurrencyClick,
            infoText = "Existing expenses keep their original currency. New totals use the saved exchange rate.",
        )
    }
}

@ThemePreviews
@Composable
private fun EditTripScreenPreview() {
    AppPreview {
        EditTripScreen(
            uiState = sampleEditTripUiState,
            onDraftChange = {},
            onSaveClick = {},
            onDeleteClick = {},
            onBackClick = {},
            onPickCurrencyClick = {},
        )
    }
}
