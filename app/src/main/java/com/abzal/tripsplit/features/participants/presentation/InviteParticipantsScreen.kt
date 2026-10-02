package com.abzal.tripsplit.features.participants.presentation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.designsystem.components.StatusPill
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.presentation.components.TripHeaderCard
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun InviteParticipantsRoute(
    onBackClick: () -> Unit,
    viewModel: InviteParticipantsViewModel = injectedViewModel { c, h ->
        InviteParticipantsViewModel(h, c.tripRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    InviteParticipantsScreen(
        uiState = uiState,
        onCreateInvitationClick = viewModel::createInvitation,
        onBackClick = onBackClick,
    )
}

@Composable
fun InviteParticipantsScreen(
    uiState: InviteParticipantsUiState,
    onCreateInvitationClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val invitation = uiState.invitation

    AppScaffold(
        topBar = { AppTopBar(title = "Invite participants", subtitle = uiState.trip?.name, onBackClick = onBackClick) },
        bottomBar = {
            BottomActionBar {
                if (invitation == null) {
                    PrimaryButton(text = "Create invite code", icon = Icons.Outlined.Link, onClick = onCreateInvitationClick)
                } else {
                    PrimaryButton(
                        text = "Share invite",
                        icon = Icons.Outlined.Send,
                        onClick = { shareInvitation(context, uiState.trip?.name.orEmpty(), invitation.code) },
                    )
                }
            }
        },
    ) {
        TripHeaderCard(trip = uiState.trip, caption = uiState.trip?.currency.orEmpty(), icon = Icons.Outlined.Groups)
        InviteCodeCard(invitation)
        InfoBanner(
            text = "Anyone with the code can join the trip as a member. They can add expenses and view balances.",
            icon = Icons.Outlined.Info,
        )
    }
}

@Composable
private fun InviteCodeCard(invitation: Invitation?) {
    AppCard {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            SectionHeader(title = "Shareable invite code")
            if (invitation != null) StatusPill(text = "Active", icon = Icons.Outlined.Link)
        }
        if (invitation == null) {
            Text(
                text = "Create a code and send it to friends. They enter it in the app to join this trip.",
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.colors.textSecondary,
            )
        } else {
            CodeBox(code = invitation.code)
        }
    }
}

@Composable
private fun CodeBox(code: String) {
    val clipboard = LocalClipboardManager.current
    Surface(shape = MaterialTheme.shapes.small, color = AppTheme.colors.background) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = code, style = MaterialTheme.typography.titleMedium, color = AppTheme.colors.textPrimary)
            IconButton(onClick = { clipboard.setText(AnnotatedString(code)) }) {
                Icon(Icons.Outlined.ContentCopy, contentDescription = "Copy code")
            }
        }
    }
}

private fun shareInvitation(context: Context, tripName: String, code: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Join \"$tripName\" on TripSplit. Invitation code: $code")
    }
    context.startActivity(Intent.createChooser(intent, "Share invite"))
}

@Preview(showBackground = true)
@Composable
private fun InviteParticipantsScreenPreview() {
    TripSplitTheme {
        InviteParticipantsScreen(
            uiState = InviteParticipantsUiState(
                trip = Trip("1", "Lisbon Friends 2026", "EUR"),
                invitation = Invitation("i", "1", "LIS26MAY"),
            ),
            onCreateInvitationClick = {},
            onBackClick = {},
        )
    }
}
