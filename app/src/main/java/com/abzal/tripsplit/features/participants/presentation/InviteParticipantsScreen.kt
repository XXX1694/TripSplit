package com.abzal.tripsplit.features.participants.presentation

import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.BottomActionBar
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.presentation.components.InviteCodeCard
import com.abzal.tripsplit.features.participants.presentation.components.TripHeaderCard

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

private fun shareInvitation(context: Context, tripName: String, code: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Join \"$tripName\" on TripSplit. Invitation code: $code")
    }
    context.startActivity(Intent.createChooser(intent, "Share invite"))
}

@ThemePreviews
@Composable
private fun InviteParticipantsScreenPreview() {
    AppPreview {
        InviteParticipantsScreen(
            uiState = sampleInviteParticipantsUiState,
            onCreateInvitationClick = {},
            onBackClick = {},
        )
    }
}
