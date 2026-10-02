package com.abzal.tripsplit.features.trips.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppCheckboxRow
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BigIconBadge
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.DangerButton
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.presentation.components.DeleteTripSummaryCard

private const val CONFIRM_WORD = "DELETE"

@Composable
fun DeleteTripRoute(
    onCancelClick: () -> Unit,
    onDeleted: () -> Unit,
    viewModel: DeleteTripViewModel = injectedViewModel { c, h ->
        DeleteTripViewModel(h, c.tripRepository, c.expenseRepository, c.participantRepository, c.balanceRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DeleteTripScreen(
        uiState = uiState,
        onConfirmClick = { viewModel.delete(onDeleted) },
        onCancelClick = onCancelClick,
    )
}

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

@Composable
private fun DeleteTripHeader(tripName: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        BigIconBadge(
            icon = Icons.Outlined.Delete,
            containerColor = AppTheme.colors.negativeContainer,
            contentColor = AppTheme.colors.negative,
        )
        Text(
            text = "Delete this trip?",
            style = MaterialTheme.typography.headlineLarge,
            color = AppTheme.colors.textPrimary,
        )
        Text(
            text = "$tripName will be permanently removed from every participant's device.",
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DeleteTripScreenPreview() {
    TripSplitTheme {
        DeleteTripScreen(
            uiState = DeleteTripUiState(
                trip = Trip("1", "Lisbon Friends 2026", "EUR"),
                expenseCount = 18,
                participantNames = listOf("Maya", "Leo", "Sam", "Nina"),
                unsettledAmount = 257.95,
            ),
            onConfirmClick = {},
            onCancelClick = {},
        )
    }
}
