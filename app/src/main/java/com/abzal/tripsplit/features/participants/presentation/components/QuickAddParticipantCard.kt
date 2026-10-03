package com.abzal.tripsplit.features.participants.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.IconBadge
import com.abzal.tripsplit.core.designsystem.components.PrimaryButton
import com.abzal.tripsplit.core.preview.AppPreview

/** Quick add: an email field and the "Add" button. */
@Composable
fun QuickAddParticipantCard(
    email: String,
    onEmailChange: (String) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(modifier = modifier) {
        AppListRow(
            title = "Add someone",
            subtitle = "Invite by email or add without an account",
            leading = { IconBadge(Icons.Outlined.PersonAdd) },
        )
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.xs), verticalAlignment = Alignment.CenterVertically) {
            AppTextField(
                value = email,
                onValueChange = onEmailChange,
                label = "name@email.com",
                leadingIcon = Icons.Outlined.Email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.weight(1f),
            )
            PrimaryButton(text = "Add", onClick = onAddClick, enabled = email.contains("@"), modifier = Modifier.weight(0.4f))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuickAddParticipantCardPreview() {
    AppPreview {
        QuickAddParticipantCard(
            email = "maya@hey.com",
            onEmailChange = {},
            onAddClick = {},
        )
    }
}
