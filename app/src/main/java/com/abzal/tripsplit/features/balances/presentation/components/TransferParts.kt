package com.abzal.tripsplit.features.balances.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.StatusPill
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.toInitials

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
    contentColor: Color = AppTheme.colors.primary,
    nameColor: Color = AppTheme.colors.textPrimary,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        PersonColumn(fromName, fromToneIndex, nameColor)
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(amountText, style = MaterialTheme.typography.titleLarge, color = contentColor)
            Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = contentColor)
                Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = contentColor, modifier = Modifier.size(18.dp))
            }
        }
        PersonColumn(toName, toToneIndex, nameColor)
    }
}

@Composable
private fun PersonColumn(name: String, toneIndex: Int, nameColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Avatar(initials = name.toInitials(), tone = avatarToneAt(toneIndex), size = 48.dp)
        Text(name.substringBefore(' '), style = MaterialTheme.typography.bodySmall, color = nameColor)
    }
}

/** One recommended payment with a "Pending" label and the "Mark as paid" button. */
@Composable
fun TransferCard(
    fromName: String,
    toName: String,
    amountText: String,
    fromToneIndex: Int,
    toToneIndex: Int,
    onMarkPaidClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(modifier = modifier) {
        TransferFlow(fromName, toName, amountText, fromToneIndex = fromToneIndex, toToneIndex = toToneIndex)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StatusPill(text = "Pending", tone = Tone.Warning, icon = Icons.Outlined.Schedule)
            AppTextButton(text = "Mark as paid", onClick = onMarkPaidClick)
        }
    }
}
