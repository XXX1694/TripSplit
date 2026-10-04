package com.abzal.tripsplit.features.expenses.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.DangerButton
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.expenses.presentation.components.ExpenseFormFields

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
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.space12)) {
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

@ThemePreviews
@Composable
private fun EditExpenseScreenPreview() {
    AppPreview {
        EditExpenseScreen(
            uiState = sampleEditExpenseUiState,
            onDraftChange = {},
            onSaveClick = {},
            onDeleteClick = {},
            onBackClick = {},
            onPickCurrencyClick = {},
        )
    }
}
