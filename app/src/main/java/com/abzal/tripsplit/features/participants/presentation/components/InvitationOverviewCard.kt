package com.abzal.tripsplit.features.participants.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.domain.model.InvitationStatus
import com.abzal.tripsplit.features.participants.presentation.InvitationManagementUiState
import com.abzal.tripsplit.features.participants.presentation.sampleInvitationManagementUiState

/** Dark card with the number of accepted, pending and revoked invitations. */
@Composable
fun InvitationOverviewCard(uiState: InvitationManagementUiState, modifier: Modifier = Modifier) {
    HeroCard(modifier = modifier) {
        Column {
            Text("Invitation overview", style = MaterialTheme.typography.titleLarge)
            Text("Organizer view · Member access by default", style = MaterialTheme.typography.bodySmall, color = AppTheme.colors.onHeroMuted)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
            InvitationStatus.entries.forEach { status ->
                CountTile(count = uiState.countOf(status), label = status.label().lowercase(), modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun CountTile(count: Int, label: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(AppTheme.colors.heroOverlay)
            .padding(horizontal = Spacing.sm, vertical = Spacing.sm),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("$count ", style = MaterialTheme.typography.titleMedium)
        Text(label, style = MaterialTheme.typography.bodySmall, color = AppTheme.colors.onHeroMuted)
    }
}

@ThemePreviews
@Composable
private fun InvitationOverviewCardPreview() {
    AppPreview {
        InvitationOverviewCard(
            uiState = sampleInvitationManagementUiState,
        )
    }
}
