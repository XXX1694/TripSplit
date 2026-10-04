package com.abzal.tripsplit.features.balances.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppTextButton
import com.abzal.tripsplit.core.designsystem.components.StatusPill
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.participants.presentation.components.label
import com.abzal.tripsplit.features.participants.presentation.components.tone

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

@ThemePreviews
@Composable
private fun TransferCardPreview() {
    AppPreview {
        TransferCard(
            fromName = "Leo Evans",
            toName = "Maya Kim",
            amountText = "104.20",
            fromToneIndex = 1,
            toToneIndex = 1,
            onMarkPaidClick = {},
        )
    }
}
