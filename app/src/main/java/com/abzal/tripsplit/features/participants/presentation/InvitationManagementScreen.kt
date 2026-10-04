package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.domain.model.InvitationStatus
import com.abzal.tripsplit.features.participants.presentation.components.FilterChips
import com.abzal.tripsplit.features.participants.presentation.components.InvitationCard
import com.abzal.tripsplit.features.participants.presentation.components.InvitationOverviewCard
import com.abzal.tripsplit.features.participants.presentation.components.InvitationSectionTitle

@Composable
fun InvitationManagementScreen(
    uiState: InvitationManagementUiState,
    onFilterChange: (InvitationStatus?) -> Unit,
    onRevokeClick: (invitationId: String) -> Unit,
    onBackClick: () -> Unit,
    onInviteClick: () -> Unit,
) {
    val clipboard = LocalClipboardManager.current
    val visible = uiState.visibleInvitations

    AppScaffold(
        topBar = { AppTopBar(title = "Invitations", subtitle = uiState.trip?.name, onBackClick = onBackClick) },
        bottomBar = {
            BottomActionBar {
                PrimaryButton(text = "Invite more participants", icon = Icons.Outlined.PersonAdd, onClick = onInviteClick)
            }
        },
    ) {
        InvitationOverviewCard(uiState)
        FilterChips(uiState, onFilterChange)

        if (visible.isEmpty()) {
            Text(
                text = "No invitations yet",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.colors.textMuted,
                textAlign = TextAlign.Center,
            )
        }
        InvitationStatus.entries.forEach { status ->
            val group = visible.filter { it.status == status }
            if (group.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                    InvitationSectionTitle(status, group.size)
                    group.forEach { invitation ->
                        InvitationCard(
                            invitation = invitation,
                            onCopyClick = { clipboard.setText(AnnotatedString(invitation.code)) },
                            onRevokeClick = { onRevokeClick(invitation.id) },
                        )
                    }
                }
            }
        }
    }
}

@ThemePreviews
@Composable
private fun InvitationManagementScreenPreview() {
    AppPreview {
        InvitationManagementScreen(
            uiState = sampleInvitationManagementUiState,
            onFilterChange = {},
            onRevokeClick = {},
            onBackClick = {},
            onInviteClick = {},
        )
    }
}
