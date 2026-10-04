package com.abzal.tripsplit.features.participants.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.DangerButton
import com.abzal.tripsplit.core.designsystem.components.IconBadge
import com.abzal.tripsplit.core.designsystem.components.SecondaryButton
import com.abzal.tripsplit.core.designsystem.components.StatusPill
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.preview.sampleInvitations
import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.domain.model.InvitationStatus

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
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.space12)) {
                SecondaryButton(text = "Copy code", icon = Icons.Outlined.ContentCopy, onClick = onCopyClick, modifier = Modifier.weight(1f))
                DangerButton(text = "Revoke", icon = Icons.Outlined.Close, onClick = onRevokeClick, modifier = Modifier.weight(1f))
            }
        }
    }
}

@ThemePreviews
@Composable
private fun InvitationCardPreview() {
    AppPreview {
        InvitationCard(
            invitation = sampleInvitations.first(),
            onCopyClick = {},
            onRevokeClick = {},
        )
    }
}
