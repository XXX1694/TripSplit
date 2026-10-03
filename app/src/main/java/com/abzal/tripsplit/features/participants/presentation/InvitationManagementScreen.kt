package com.abzal.tripsplit.features.participants.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppChip
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.domain.model.InvitationStatus
import com.abzal.tripsplit.features.participants.presentation.components.InvitationCard
import com.abzal.tripsplit.features.participants.presentation.components.InvitationOverviewCard
import com.abzal.tripsplit.features.participants.presentation.components.InvitationSectionTitle
import com.abzal.tripsplit.features.participants.presentation.components.label
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun InvitationManagementRoute(
    onBackClick: () -> Unit,
    onInviteClick: () -> Unit,
    viewModel: InvitationManagementViewModel = injectedViewModel { c, h ->
        InvitationManagementViewModel(h, c.tripRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    InvitationManagementScreen(
        uiState = uiState,
        onFilterChange = viewModel::onFilterChange,
        onRevokeClick = viewModel::revoke,
        onBackClick = onBackClick,
        onInviteClick = onInviteClick,
    )
}

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
                color = AppTheme.colors.textSecondary,
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

@Composable
private fun FilterChips(uiState: InvitationManagementUiState, onFilterChange: (InvitationStatus?) -> Unit) {
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

@Preview(showBackground = true)
@Composable
private fun InvitationManagementScreenPreview() {
    TripSplitTheme {
        InvitationManagementScreen(
            uiState = InvitationManagementUiState(
                trip = Trip("1", "Lisbon Friends 2026", "EUR"),
                invitations = listOf(
                    Invitation("1", "1", "LIS26MAY", InvitationStatus.ACCEPTED),
                    Invitation("2", "1", "KQ4PX7ZD"),
                    Invitation("3", "1", "OLD00001", InvitationStatus.REVOKED),
                ),
            ),
            onFilterChange = {},
            onRevokeClick = {},
            onBackClick = {},
            onInviteClick = {},
        )
    }
}
