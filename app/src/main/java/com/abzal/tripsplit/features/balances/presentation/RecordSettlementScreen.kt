package com.abzal.tripsplit.features.balances.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AmountCard
import com.abzal.tripsplit.core.designsystem.components.AppDateField
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatMoney
import com.abzal.tripsplit.features.balances.presentation.components.TransferFlow
import com.abzal.tripsplit.features.participants.presentation.components.ParticipantSelect
import com.abzal.tripsplit.features.participants.presentation.components.label

@Composable
fun RecordSettlementScreen(
    uiState: RecordSettlementUiState,
    onDraftChange: (SettlementDraft) -> Unit,
    onRecordClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val draft = uiState.draft

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Record payment",
                subtitle = uiState.trip?.name,
                onBackClick = onBackClick,
                actions = { AppTextButton(text = "Save", onClick = onRecordClick, enabled = draft.isValid) },
            )
        },
        bottomBar = {
            BottomActionBar {
                PrimaryButton(
                    text = "Record payment",
                    icon = Icons.Outlined.CheckCircle,
                    onClick = onRecordClick,
                    enabled = draft.isValid,
                    isLoading = uiState.isSaving,
                )
            }
        },
    ) {
        HeroCard {
            Text("PAYMENT", style = MaterialTheme.typography.labelSmall, color = AppTheme.colors.onHeroMuted)
            TransferFlow(
                fromName = uiState.nameOf(draft.fromId),
                toName = uiState.nameOf(draft.toId),
                amountText = draft.amount?.let { formatMoney(it, uiState.currency) } ?: "—",
                fromToneIndex = uiState.toneIndexOf(draft.fromId),
                toToneIndex = uiState.toneIndexOf(draft.toId),
                contentColor = AppTheme.colors.onHero,
                nameColor = AppTheme.colors.onHero,
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            ParticipantSelect(
                label = "Paid by",
                participants = uiState.participants,
                selectedId = draft.fromId,
                onSelect = { onDraftChange(draft.copy(fromId = it)) },
                modifier = Modifier.weight(1f),
            )
            ParticipantSelect(
                label = "Received by",
                participants = uiState.participants,
                selectedId = draft.toId,
                onSelect = { onDraftChange(draft.copy(toId = it)) },
                modifier = Modifier.weight(1f),
            )
        }
        AmountCard(
            amountText = draft.amountText,
            currency = uiState.currency,
            onAmountChange = { onDraftChange(draft.copy(amountText = it)) },
            onCurrencyClick = {},
        )
        AppDateField(
            label = "Payment date",
            dateMillis = draft.dateMillis,
            onDateSelected = { onDraftChange(draft.copy(dateMillis = it)) },
            leadingIcon = Icons.Outlined.CalendarToday,
        )
    }
}

@ThemePreviews
@Composable
private fun RecordSettlementScreenPreview() {
    AppPreview {
        RecordSettlementScreen(
            uiState = sampleRecordSettlementUiState,
            onDraftChange = {},
            onRecordClick = {},
            onBackClick = {},
        )
    }
}
