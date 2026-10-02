package com.abzal.tripsplit.features.expenses.presentation

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
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseFormFields
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun AddExpenseRoute(
    onBackClick: () -> Unit,
    onSaved: () -> Unit,
    onPickCurrencyClick: () -> Unit,
    pickedCurrency: String?,
    viewModel: AddExpenseViewModel = injectedViewModel { c, h ->
        AddExpenseViewModel(h, c.tripRepository, c.expenseRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(pickedCurrency) {
        if (pickedCurrency != null) viewModel.onCurrencyPicked(pickedCurrency)
    }

    AddExpenseScreen(
        uiState = uiState,
        onDraftChange = viewModel::onDraftChange,
        onSaveClick = { viewModel.save(onSaved) },
        onBackClick = onBackClick,
        onPickCurrencyClick = onPickCurrencyClick,
    )
}

@Composable
fun AddExpenseScreen(
    uiState: AddExpenseUiState,
    onDraftChange: (ExpenseDraft) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    onPickCurrencyClick: () -> Unit,
) {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Add expense",
                subtitle = uiState.trip?.name,
                onBackClick = onBackClick,
                actions = { AppTextButton(text = "Save", onClick = onSaveClick, enabled = uiState.draft.isValid) },
            )
        },
        bottomBar = {
            BottomActionBar {
                PrimaryButton(
                    text = "Add expense",
                    icon = Icons.Outlined.Add,
                    onClick = onSaveClick,
                    enabled = uiState.draft.isValid,
                    isLoading = uiState.isSaving,
                )
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
private fun AddExpenseScreenPreview() {
    val participants = listOf(Participant("a", "1", "Maya Kim"), Participant("b", "1", "Leo Evans"), Participant("c", "1", "Sam Adeyemi"))
    TripSplitTheme {
        AddExpenseScreen(
            uiState = AddExpenseUiState(
                trip = Trip("1", "Lisbon Friends 2026", "EUR"),
                participants = participants,
                draft = ExpenseDraft(paidById = "a", participantIds = participants.map { it.id }.toSet()),
            ),
            onDraftChange = {},
            onSaveClick = {},
            onBackClick = {},
            onPickCurrencyClick = {},
        )
    }
}
