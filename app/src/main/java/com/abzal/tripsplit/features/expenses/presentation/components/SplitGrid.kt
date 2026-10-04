package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.preview.sampleParticipants
import com.abzal.tripsplit.features.participants.domain.model.Participant

/** Participants in two columns; tap to include or exclude from the split. */
@Composable
fun SplitGrid(participants: List<Participant>, selectedIds: Set<String>, onToggle: (String) -> Unit) {
    val percent = if (selectedIds.isEmpty()) 0 else 100 / selectedIds.size
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.space12)) {
        participants.chunked(2).forEach { pair ->
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.space12)) {
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

@ThemePreviews
@Composable
private fun SplitGridPreview() {
    AppPreview {
        SplitGrid(
            participants = sampleParticipants,
            selectedIds = sampleParticipants.take(2).map { it.id }.toSet(),
            onToggle = {},
        )
    }
}
