package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppChip
import com.abzal.tripsplit.core.designsystem.components.AppSelectField
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.Overline
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.util.currencySymbol
import com.abzal.tripsplit.features.expenses.presentation.ExpenseDraft
import com.abzal.tripsplit.features.participants.domain.model.Participant

/** All fields of the add / edit expense forms. State lives in the caller. */
@Composable
fun ExpenseFormFields(
    draft: ExpenseDraft,
    participants: List<Participant>,
    onDraftChange: (ExpenseDraft) -> Unit,
    onPickCurrencyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
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
        PaidBySelect(
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

/** Big bordered card: currency chip on the left, amount input on the right. */
@Composable
private fun AmountCard(
    amountText: String,
    currency: String,
    onAmountChange: (String) -> Unit,
    onCurrencyClick: () -> Unit,
) {
    val colors = AppTheme.colors
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = colors.surface,
        border = BorderStroke(2.dp, colors.primary),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(onClick = onCurrencyClick, shape = RoundedCornerShape(50), color = colors.primaryContainer) {
                Row(
                    modifier = Modifier.padding(horizontal = Spacing.sm, vertical = Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(currency, style = MaterialTheme.typography.labelLarge, color = colors.primary)
                    Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null, tint = colors.primary, modifier = Modifier.size(18.dp))
                }
            }
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                Overline("Amount")
                AmountInput(amountText, currencySymbol(currency), onAmountChange)
            }
        }
    }
}

@Composable
private fun AmountInput(text: String, symbol: String, onChange: (String) -> Unit) {
    val colors = AppTheme.colors
    val style = MaterialTheme.typography.headlineLarge.copy(
        color = if (text.isEmpty()) colors.textDisabled else colors.textPrimary,
        textAlign = TextAlign.End,
    )
    BasicTextField(
        value = text,
        onValueChange = onChange,
        singleLine = true,
        textStyle = style,
        cursorBrush = SolidColor(colors.primary),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        decorationBox = { inner ->
            Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                Text(symbol, style = style.copy(color = colors.textDisabled))
                Box(contentAlignment = Alignment.CenterEnd) {
                    if (text.isEmpty()) Text("0.00", style = style)
                    inner()
                }
            }
        },
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoryChips(selected: String, onSelect: (String) -> Unit) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(Spacing.xs), verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        expenseCategories.forEach { category ->
            AppChip(
                text = category,
                icon = categoryIcon(category),
                selected = category == selected,
                onClick = { onSelect(category) },
            )
        }
    }
}

@Composable
private fun PaidBySelect(participants: List<Participant>, selectedId: String?, onSelect: (String) -> Unit) {
    var isOpen by remember { mutableStateOf(false) }
    val selected = participants.firstOrNull { it.id == selectedId }

    Box {
        AppSelectField(
            label = "Paid by",
            value = selected?.name.orEmpty(),
            onClick = { isOpen = true },
            leadingContent = { Avatar(initials = selected?.name.orEmpty().toInitials()) },
            trailing = { Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null) },
        )
        DropdownMenu(expanded = isOpen, onDismissRequest = { isOpen = false }) {
            participants.forEach { participant ->
                DropdownMenuItem(
                    text = { Text(participant.name) },
                    onClick = {
                        isOpen = false
                        onSelect(participant.id)
                    },
                )
            }
        }
    }
}

@Composable
private fun SplitHeader(draft: ExpenseDraft) {
    val count = draft.participantIds.size
    val share = draft.amount?.takeIf { count > 0 }?.let { "%.2f".format(it / count) }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Overline("Split between")
        Text(
            text = "Equally · " + (share?.let { "${draft.currency} $it each" } ?: "$count people"),
            style = MaterialTheme.typography.labelMedium,
            color = AppTheme.colors.positive,
        )
    }
}

/** Participants in two columns; tap to include or exclude from the split. */
@Composable
private fun SplitGrid(participants: List<Participant>, selectedIds: Set<String>, onToggle: (String) -> Unit) {
    val percent = if (selectedIds.isEmpty()) 0 else 100 / selectedIds.size
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
        participants.chunked(2).forEach { pair ->
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                pair.forEach { participant ->
                    val isSelected = participant.id in selectedIds
                    SplitTile(
                        participant = participant,
                        toneIndex = participants.indexOf(participant),
                        isSelected = isSelected,
                        percent = if (isSelected) percent else null,
                        onClick = { onToggle(participant.id) },
                        modifier = Modifier.weight(1f),
                    )
                }
                if (pair.size == 1) Box(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun SplitTile(
    participant: Participant,
    toneIndex: Int,
    isSelected: Boolean,
    percent: Int?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) colors.primaryContainer else colors.surface,
        border = BorderStroke(1.dp, if (isSelected) colors.primary else colors.outline),
    ) {
        Row(
            modifier = Modifier.padding(Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            Avatar(initials = participant.name.toInitials(), tone = avatarToneAt(toneIndex), size = 28.dp)
            Text(
                text = participant.name.substringBefore(' '),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleSmall,
                color = colors.textPrimary,
            )
            if (percent != null) {
                Text("$percent%", style = MaterialTheme.typography.bodySmall, color = colors.textSecondary)
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Included",
                    tint = colors.onPrimary,
                    modifier = Modifier.size(20.dp).background(colors.primary, RoundedCornerShape(6.dp)),
                )
            }
        }
    }
}
