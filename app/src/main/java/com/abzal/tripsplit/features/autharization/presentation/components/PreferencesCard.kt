package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.components.AppDivider
import com.abzal.tripsplit.core.designsystem.components.AppSwitch
import com.abzal.tripsplit.core.designsystem.components.RowValue
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.balances.domain.model.Settlement
import com.abzal.tripsplit.features.expenses.domain.model.Expense

@Composable
fun PreferencesCard() {
    var expenseNotifications by remember { mutableStateOf(true) }
    var settlementReminders by remember { mutableStateOf(true) }

    SettingsCard {
        SettingsRow(
            icon = Icons.Outlined.AttachMoney,
            title = "Default currency",
            subtitle = "Used when creating new trips",
            trailing = { RowValue("EUR  €") },
        )
        AppDivider()
        SettingsRow(
            icon = Icons.Outlined.Notifications,
            title = "Expense notifications",
            subtitle = "New expenses and edits",
            trailing = { AppSwitch(expenseNotifications, { expenseNotifications = it }) },
        )
        AppDivider()
        SettingsRow(
            icon = Icons.Outlined.Savings,
            title = "Settlement reminders",
            subtitle = "Weekly while balances are open",
            trailing = { AppSwitch(settlementReminders, { settlementReminders = it }) },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreferencesCardPreview() {
    AppPreview {
        PreferencesCard()
    }
}
