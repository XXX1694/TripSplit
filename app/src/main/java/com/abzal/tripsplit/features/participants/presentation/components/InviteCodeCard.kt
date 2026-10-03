package com.abzal.tripsplit.features.participants.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.designsystem.components.StatusPill
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.sampleInvitations
import com.abzal.tripsplit.features.participants.domain.model.Invitation

@Composable
fun InviteCodeCard(invitation: Invitation?) {
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

@Preview(showBackground = true)
@Composable
private fun InviteCodeCardPreview() {
    AppPreview {
        InviteCodeCard(
            invitation = sampleInvitations.first(),
        )
    }
}
