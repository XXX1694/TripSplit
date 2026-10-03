package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.HeroCard
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.sampleUser
import com.abzal.tripsplit.features.autharization.domain.model.User

@Composable
fun ProfileHeader(user: User?) {
    HeroCard {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
            Avatar(initials = (user?.name ?: "").toInitials(), size = Sizes.avatarLarge)
            Column(modifier = Modifier.weight(1f)) {
                Text(text = user?.name ?: "", style = MaterialTheme.typography.titleLarge)
                Text(text = user?.email ?: "", style = MaterialTheme.typography.bodyMedium, color = AppTheme.colors.onHeroMuted)
                Text(text = "Verified account", style = MaterialTheme.typography.bodySmall, color = AppTheme.colors.onHeroMuted)
            }
            Box(
                modifier = Modifier.size(Sizes.iconBadge).clip(MaterialTheme.shapes.small).background(AppTheme.colors.heroOverlay),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.Edit, contentDescription = "Edit profile", modifier = Modifier.size(Sizes.icon))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileHeaderPreview() {
    AppPreview {
        ProfileHeader(
            user = sampleUser,
        )
    }
}
