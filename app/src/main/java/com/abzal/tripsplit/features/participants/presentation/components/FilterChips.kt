package com.abzal.tripsplit.features.participants.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppChip
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.domain.model.InvitationStatus
import com.abzal.tripsplit.features.participants.presentation.InvitationManagementUiState
import com.abzal.tripsplit.features.participants.presentation.components.label
import com.abzal.tripsplit.features.participants.presentation.sampleInvitationManagementUiState

@Composable
fun FilterChips(uiState: InvitationManagementUiState, onFilterChange: (InvitationStatus?) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        AppChip(
            text = "All ${uiState.invitations.size}",
            selected = uiState.statusFilter == null,
            onClick = { onFilterChange(null) },
        )
        InvitationStatus.entries.forEach { status ->
            AppChip(
                text = "${status.label()} ${uiState.countOf(status)}",
                selected = uiState.statusFilter == status,
                onClick = { onFilterChange(status) },
            )
        }
    }
}

@ThemePreviews
@Composable
private fun FilterChipsPreview() {
    AppPreview {
        FilterChips(
            uiState = sampleInvitationManagementUiState,
            onFilterChange = {},
        )
    }
}
