package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppCard
import com.abzal.tripsplit.core.designsystem.components.AppDivider
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppSwitch
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.ChevronIcon
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.IconBadge
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.Overline
import com.abzal.tripsplit.core.designsystem.components.RowValue
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.autharization.domain.model.User

@Composable
fun ProfileRoute(
    onBackClick: () -> Unit,
    onSignedOut: () -> Unit,
    viewModel: ProfileViewModel = injectedViewModel { c, _ -> ProfileViewModel(c.authRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProfileScreen(
        uiState = uiState,
        onSignOutClick = { viewModel.signOut(onSignedOut) },
        onClearLocalDataClick = { viewModel.clearLocalData(onSignedOut) },
        onBackClick = onBackClick,
    )
}

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onSignOutClick: () -> Unit,
    onClearLocalDataClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    AppScaffold(
        topBar = { AppTopBar(title = "Profile & settings", onBackClick = onBackClick) },
    ) {
        ProfileHeader(user = uiState.user)

        Overline("Preferences")
        PreferencesCard()

        Overline("Local data")
        LocalDataCard()

        InfoBanner(
            text = "Your local data is encrypted. Changes sync automatically when you're online.",
            icon = Icons.Outlined.Shield,
            tone = Tone.Primary,
        )
        AccountCard(onSignOutClick = onSignOutClick, onDeleteAccountClick = onClearLocalDataClick)
        AppVersionText()
    }
}

@Composable
private fun ProfileHeader(user: User?) {
    HeroCard {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
            Avatar(initials = (user?.name ?: "").toInitials(), size = 56.dp)
            Column(modifier = Modifier.weight(1f)) {
                Text(text = user?.name ?: "", style = MaterialTheme.typography.titleLarge)
                Text(text = user?.email ?: "", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.8f))
                Text(text = "Verified account", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
            }
            Box(
                modifier = Modifier.size(40.dp).clip(MaterialTheme.shapes.small).background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.Edit, contentDescription = "Edit profile", modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
private fun PreferencesCard() {
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

@Composable
private fun LocalDataCard() {
    SettingsCard {
        SettingsRow(
            icon = Icons.Outlined.Storage,
            title = "Stored on this device",
            subtitle = "23.8 MB · Updated just now",
            trailing = { RowValue("Healthy") },
        )
        AppDivider()
        SettingsRow(
            icon = Icons.Outlined.Download,
            title = "Export all trip data",
            subtitle = "CSV and JSON archive",
            trailing = { ChevronIcon() },
        )
        AppDivider()
        SettingsRow(
            icon = Icons.Outlined.CloudOff,
            title = "Offline persistence",
            subtitle = "Available offline",
            trailing = { RowValue("On") },
        )
    }
}

@Composable
private fun AccountCard(onSignOutClick: () -> Unit, onDeleteAccountClick: () -> Unit) {
    SettingsCard {
        SettingsRow(
            icon = Icons.AutoMirrored.Outlined.Logout,
            title = "Sign out",
            subtitle = "Keep local data on this device",
            trailing = { ChevronIcon() },
            onClick = onSignOutClick,
        )
        AppDivider()
        SettingsRow(
            icon = Icons.Outlined.PersonRemove,
            title = "Delete account",
            subtitle = "Permanently remove profile and local data",
            tone = Tone.Negative,
            trailing = { ChevronIcon() },
            onClick = onDeleteAccountClick,
        )
    }
}

@Composable
private fun AppVersionText() {
    val context = LocalContext.current
    val version = remember {
        runCatching { context.packageManager.getPackageInfo(context.packageName, 0).versionName }.getOrNull()
    }
    Text(
        text = "TripSplit for Android · Version ${version ?: "-"}",
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodySmall,
        color = AppTheme.colors.textDisabled,
        textAlign = TextAlign.Center,
    )
}

/** Card without inner gaps: rows are separated by dividers. */
@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    AppCard(verticalArrangement = Arrangement.spacedBy(0.dp), content = content)
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    tone: Tone = Tone.Primary,
    trailing: @Composable () -> Unit,
    onClick: (() -> Unit)? = null,
) {
    AppListRow(
        title = title,
        subtitle = subtitle,
        modifier = modifier,
        onClick = onClick,
        leading = { IconBadge(icon = icon, tone = tone) },
        trailing = trailing,
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    TripSplitTheme {
        ProfileScreen(
            uiState = ProfileUiState(user = User("1", "Maya Kim", "maya@hey.com")),
            onSignOutClick = {},
            onClearLocalDataClick = {},
            onBackClick = {},
        )
    }
}
