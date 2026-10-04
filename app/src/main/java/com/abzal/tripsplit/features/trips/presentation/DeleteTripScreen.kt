package com.abzal.tripsplit.features.trips.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.components.AppCheckboxRow
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.DangerButton
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.presentation.components.label
import com.abzal.tripsplit.features.participants.presentation.components.tone
import com.abzal.tripsplit.features.trips.presentation.components.DeleteTripHeader
import com.abzal.tripsplit.features.trips.presentation.components.DeleteTripSummaryCard

private const val CONFIRM_WORD = "DELETE"

@Composable
fun DeleteTripScreen(
    uiState: DeleteTripUiState,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    var typedWord by remember { mutableStateOf("") }
    var isUnderstood by remember { mutableStateOf(false) }
    val tripName = uiState.trip?.name.orEmpty()

    AppScaffold(
        topBar = { AppTopBar(title = "Delete trip", subtitle = tripName, onBackClick = onCancelClick) },
        bottomBar = {
            BottomActionBar {
                DangerButton(
                    text = "Delete $tripName",
                    icon = Icons.Outlined.Delete,
                    onClick = onConfirmClick,
                    enabled = typedWord == CONFIRM_WORD && isUnderstood && !uiState.isDeleting,
                )
                AppTextButton(text = "Keep trip", onClick = onCancelClick, modifier = Modifier.fillMaxWidth())
            }
        },
    ) {
        DeleteTripHeader(tripName)
        DeleteTripSummaryCard(uiState)
        InfoBanner(
            text = "This cannot be undone. Export your trip before deleting if you need a copy.",
            icon = Icons.Outlined.WarningAmber,
            tone = Tone.Negative,
        )
        // red style marks the dangerous action
        AppTextField(
            value = typedWord,
            onValueChange = { typedWord = it },
            label = "Type $CONFIRM_WORD to confirm",
            leadingIcon = Icons.Outlined.Keyboard,
            isError = true,
        )
        AppCheckboxRow(
            text = "I understand all trip data will be permanently deleted.",
            checked = isUnderstood,
            onCheckedChange = { isUnderstood = it },
        )
    }
}

@ThemePreviews
@Composable
private fun DeleteTripScreenPreview() {
    AppPreview {
        DeleteTripScreen(
            uiState = sampleDeleteTripUiState,
            onConfirmClick = {},
            onCancelClick = {},
        )
    }
}
