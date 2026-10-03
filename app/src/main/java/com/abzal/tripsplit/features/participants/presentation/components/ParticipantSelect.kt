package com.abzal.tripsplit.features.participants.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.components.AppSelectField
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.features.participants.domain.model.Participant

/** Field with the chosen participant that opens a menu of all [participants]: "Paid by", "Received by". */
@Composable
fun ParticipantSelect(
    label: String,
    participants: List<Participant>,
    selectedId: String?,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isOpen by remember { mutableStateOf(false) }
    val selected = participants.firstOrNull { it.id == selectedId }

    Box(modifier = modifier) {
        AppSelectField(
            label = label,
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
