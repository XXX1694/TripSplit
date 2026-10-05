package com.abzal.tripsplit.features.balances.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.captionMedium
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.presentation.components.tone

/**
 * Payer on the left, receiver on the right and the amount with an arrow between them.
 * Pass white [contentColor] to use it on a dark card.
 */
@Composable
fun TransferFlow(
    fromName: String,
    toName: String,
    amountText: String,
    modifier: Modifier = Modifier,
    fromToneIndex: Int = 0,
    toToneIndex: Int = 1,
    contentColor: Color = AppTheme.colors.accent,
    nameColor: Color = AppTheme.colors.text,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.space16),
    ) {
        PersonColumn(fromName, fromToneIndex, nameColor)
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(amountText, style = MaterialTheme.typography.displaySmall, color = contentColor)
            Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = contentColor)
                Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = contentColor, modifier = Modifier.size(Sizes.iconSmall))
            }
        }
        PersonColumn(toName, toToneIndex, nameColor)
    }
}

@Composable
private fun PersonColumn(name: String, toneIndex: Int, nameColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Avatar(initials = name.toInitials(), tone = avatarToneAt(toneIndex), size = Sizes.avatarMedium)
        Text(name.substringBefore(' '), style = MaterialTheme.typography.captionMedium, color = nameColor)
    }
}

@ThemePreviews
@Composable
private fun TransferFlowPreview() {
    AppPreview {
        TransferFlow(
            fromName = "Leo Evans",
            toName = "Maya Kim",
            amountText = "104.20",
        )
    }
}
