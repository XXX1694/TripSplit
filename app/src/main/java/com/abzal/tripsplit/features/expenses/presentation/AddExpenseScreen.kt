package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseFormFields

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

@ThemePreviews
@Composable
private fun AddExpenseScreenPreview() {
    AppPreview {
        AddExpenseScreen(
            uiState = sampleAddExpenseUiState,
            onDraftChange = {},
            onSaveClick = {},
            onBackClick = {},
            onPickCurrencyClick = {},
        )
    }
}
