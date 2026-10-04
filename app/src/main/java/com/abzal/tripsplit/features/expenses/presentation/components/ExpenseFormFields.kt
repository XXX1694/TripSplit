package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AmountCard
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.Overline
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.preview.sampleParticipants
import com.abzal.tripsplit.features.expenses.presentation.ExpenseDraft
import com.abzal.tripsplit.features.expenses.presentation.sampleExpenseDraft
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.participants.presentation.components.ParticipantSelect
import com.abzal.tripsplit.features.participants.presentation.components.label

/** All fields of the add / edit expense forms. State lives in the caller. */
@Composable
fun ExpenseFormFields(
    draft: ExpenseDraft,
    participants: List<Participant>,
    onDraftChange: (ExpenseDraft) -> Unit,
    onPickCurrencyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Spacing.space12)) {
        AmountCard(
            amountText = draft.amountText,
            currency = draft.currency,
            onAmountChange = { onDraftChange(draft.copy(amountText = it)) },
            onCurrencyClick = onPickCurrencyClick,
        )
        AppTextField(
            value = draft.title,
            onValueChange = { onDraftChange(draft.copy(title = it)) },
            label = "Description",
            placeholder = "What was this for?",
            leadingIcon = Icons.Outlined.Receipt,
        )
        Overline("Category")
        CategoryChips(selected = draft.category, onSelect = { onDraftChange(draft.copy(category = it)) })
        ParticipantSelect(
            label = "Paid by",
            participants = participants,
            selectedId = draft.paidById,
            onSelect = { onDraftChange(draft.copy(paidById = it)) },
        )
        SplitHeader(draft)
        SplitGrid(
            participants = participants,
            selectedIds = draft.participantIds,
            onToggle = { onDraftChange(draft.toggleParticipant(it)) },
        )
        InfoBanner(
            text = "Enter an amount to preview each person's share.",
            icon = Icons.Outlined.Calculate,
        )
    }
}

@ThemePreviews
@Composable
private fun ExpenseFormFieldsPreview() {
    AppPreview {
        ExpenseFormFields(
            draft = sampleExpenseDraft,
            participants = sampleParticipants,
            onDraftChange = {},
            onPickCurrencyClick = {},
        )
    }
}
