package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import androidx.compose.foundation.layout.Arrangement
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.DangerButton
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseFormFields
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun EditExpenseRoute(
    onBackClick: () -> Unit,
    onSaved: () -> Unit,
    onDeleted: () -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
    viewModel: EditExpenseViewModel = injectedViewModel { c, h ->
        EditExpenseViewModel(h, c.tripRepository, c.expenseRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(pickedCurrency) {
        if (pickedCurrency != null) viewModel.onCurrencyPicked(pickedCurrency)
    }

    EditExpenseScreen(
        uiState = uiState,
        onDraftChange = viewModel::onDraftChange,
        onSaveClick = { viewModel.save(onSaved) },
        onDeleteClick = { viewModel.delete(onDeleted) },
        onBackClick = onBackClick,
        onPickCurrencyClick = onPickCurrencyClick,
    )
}

@Composable
fun EditExpenseScreen(
    uiState: EditExpenseUiState,
    onDraftChange: (ExpenseDraft) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    onPickCurrencyClick: () -> Unit,
) {
    val canSave = uiState.isLoaded && uiState.draft.isValid

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Edit expense",
                subtitle = uiState.trip?.name,
                onBackClick = onBackClick,
                actions = { AppTextButton(text = "Save", onClick = onSaveClick, enabled = canSave) },
            )
        },
        bottomBar = {
            BottomActionBar {
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                    DangerButton(
                        text = "Delete",
                        icon = Icons.Outlined.Delete,
                        onClick = onDeleteClick,
                        modifier = Modifier.weight(1f),
                    )
                    PrimaryButton(
                        text = "Save expense",
                        icon = Icons.Outlined.Check,
                        onClick = onSaveClick,
                        enabled = canSave,
                        isLoading = uiState.isSaving,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        },
    ) {
        ExpenseFormFields(
            draft = uiState.draft,
            participants = uiState.participants,
            onDraftChange = onDraftChange,
            onPickCurrencyClick = onPickCurrencyClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditExpenseScreenPreview() {
    val participants = listOf(Participant("a", "1", "Maya Kim"), Participant("b", "1", "Leo Evans"))
    TripSplitTheme {
        EditExpenseScreen(
            uiState = EditExpenseUiState(
                trip = Trip("1", "Lisbon Friends 2026", "EUR"),
                participants = participants,
                draft = ExpenseDraft("Dinner at Prado", "148.0", "EUR", "Food", "a", setOf("a", "b")),
                isLoaded = true,
            ),
            onDraftChange = {},
            onSaveClick = {},
            onDeleteClick = {},
            onBackClick = {},
            onPickCurrencyClick = {},
        )
    }
}
