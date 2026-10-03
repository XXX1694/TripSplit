package com.abzal.tripsplit.features.participants.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxHeight
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.DangerButton
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.IconBadge
import com.abzal.tripsplit.core.designsystem.components.SecondaryButton
import com.abzal.tripsplit.core.designsystem.components.StatusPill
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.domain.model.InvitationStatus
import com.abzal.tripsplit.features.participants.presentation.InvitationManagementUiState

fun InvitationStatus.label(): String = when (this) {
    InvitationStatus.ACCEPTED -> "Accepted"
    InvitationStatus.PENDING -> "Pending"
    InvitationStatus.REVOKED -> "Revoked"
}

fun InvitationStatus.tone(): Tone = when (this) {
    InvitationStatus.ACCEPTED -> Tone.Primary
    InvitationStatus.PENDING -> Tone.Warning
    InvitationStatus.REVOKED -> Tone.Negative
}

/** Dark card with the number of accepted, pending and revoked invitations. */
@Composable
fun InvitationOverviewCard(uiState: InvitationManagementUiState, modifier: Modifier = Modifier) {
    HeroCard(modifier = modifier) {
        Column {
            Text("Invitation overview", style = MaterialTheme.typography.titleLarge)
            Text("Organizer view · Member access by default", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
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
            .background(Color.White.copy(alpha = 0.12f))
            .padding(horizontal = Spacing.sm, vertical = Spacing.sm),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("$count ", style = MaterialTheme.typography.titleMedium)
        Text(label, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
    }
}

/** One invitation code with its status; pending ones can be copied or revoked. */
@Composable
fun InvitationCard(
    invitation: Invitation,
    onCopyClick: () -> Unit,
    onRevokeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val status = invitation.status
    AppCard(modifier = modifier) {
        AppListRow(
            title = invitation.code,
            subtitle = "Invitation code",
            leading = { IconBadge(Icons.Outlined.VpnKey, tone = status.tone()) },
            trailing = { StatusPill(text = status.label(), tone = status.tone()) },
        )
        if (status == InvitationStatus.PENDING) {
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                SecondaryButton(text = "Copy code", icon = Icons.Outlined.ContentCopy, onClick = onCopyClick, modifier = Modifier.weight(1f))
                DangerButton(text = "Revoke", icon = Icons.Outlined.Close, onClick = onRevokeClick, modifier = Modifier.weight(1f))
            }
        }
    }
}

/** "PENDING" on the left and "2 invitations" on the right. */
@Composable
fun InvitationSectionTitle(status: InvitationStatus, count: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(status.label().uppercase(), style = MaterialTheme.typography.labelSmall, color = AppTheme.colors.textSecondary)
        Text("$count invitations", style = MaterialTheme.typography.bodySmall, color = AppTheme.colors.textSecondary, textAlign = TextAlign.End)
    }
}
