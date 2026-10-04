package com.abzal.tripsplit.features.participants.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.preview.sampleParticipants
import com.abzal.tripsplit.features.participants.domain.model.Participant

/** Avatar, name, email and a pencil button. */
@Composable
fun ParticipantRow(
    participant: Participant,
    toneIndex: Int,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppListRow(
        title = participant.name,
        subtitle = participant.email,
        modifier = modifier,
        leading = { Avatar(initials = participant.name.toInitials(), tone = avatarToneAt(toneIndex), size = Sizes.avatarMedium) },
        trailing = {
            IconButton(onClick = onEditClick) { Icon(Icons.Outlined.Edit, contentDescription = "Edit") }
        },
    )
}

@ThemePreviews
@Composable
private fun ParticipantRowPreview() {
    AppPreview {
        ParticipantRow(
            participant = sampleParticipants.first(),
            toneIndex = 0,
            onEditClick = {},
        )
    }
}
