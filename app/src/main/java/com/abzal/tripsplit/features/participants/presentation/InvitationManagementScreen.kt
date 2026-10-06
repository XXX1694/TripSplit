package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.outlined.VpnKey
import com.abzal.tripsplit.core.designsystem.components.EmptyState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import com.abzal.tripsplit.core.designsystem.components.AppLazyScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
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

    AppLazyScaffold(
        topBar = { AppTopBar(title = "Invitations", subtitle = uiState.trip?.name, onBackClick = onBackClick) },
        bottomBar = {
            BottomActionBar {
                PrimaryButton(text = "Invite more participants", icon = Icons.Outlined.PersonAdd, onClick = onInviteClick)
            }
        },
    ) {
        item { InvitationOverviewCard(uiState) }
        item { FilterChips(uiState, onFilterChange) }

        if (visible.isEmpty()) {
            item {
                EmptyState(
                    title = "No invitations yet",
                    message = "Create an invitation code and share it with friends.",
                    icon = Icons.Outlined.VpnKey,
                    actionText = "Invite participants",
                    onActionClick = onInviteClick,
                )
            }
        }
        InvitationStatus.entries.forEach { status ->
            val group = visible.filter { it.status == status }
            if (group.isNotEmpty()) {
                item(key = "title-${status.name}") { InvitationSectionTitle(status, group.size) }
                items(group, key = { it.id }) { invitation ->
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
