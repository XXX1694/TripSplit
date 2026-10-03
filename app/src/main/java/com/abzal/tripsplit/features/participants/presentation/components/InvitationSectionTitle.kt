package com.abzal.tripsplit.features.participants.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.participants.domain.model.InvitationStatus

/** "PENDING" on the left and "2 invitations" on the right. */
@Composable
fun InvitationSectionTitle(status: InvitationStatus, count: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(status.label().uppercase(), style = MaterialTheme.typography.labelSmall, color = AppTheme.colors.textSecondary)
        Text("$count invitations", style = MaterialTheme.typography.bodySmall, color = AppTheme.colors.textSecondary, textAlign = TextAlign.End)
    }
}

@Preview(showBackground = true)
@Composable
private fun InvitationSectionTitlePreview() {
    AppPreview {
        InvitationSectionTitle(
            status = InvitationStatus.PENDING,
            count = 3,
        )
    }
}
