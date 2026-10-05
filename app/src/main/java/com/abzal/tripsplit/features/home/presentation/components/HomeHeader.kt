package com.abzal.tripsplit.features.home.presentation.components

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.components.Avatar
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.designsystem.components.toInitials
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.formatToday

/** "TUESDAY, 29 SEP / Where to next, Maya?" with the profile avatar on the right. */
@Composable
fun HomeGreeting(
    userName: String,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = formatToday(),
                style = MaterialTheme.typography.labelMedium,
                color = AppTheme.colors.accent,
            )
            Text(
                text = "Where to next, ${userName.substringBefore(' ')}?",
                style = MaterialTheme.typography.headlineMedium,
                color = AppTheme.colors.text,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Box(
            modifier = Modifier.size(Sizes.touchTarget).clip(CircleShape).clickable(onClick = onProfileClick),
            contentAlignment = Alignment.Center,
        ) {
            Avatar(initials = userName.toInitials())
        }
    }
}

@ThemePreviews
@Composable
private fun HomeGreetingPreview() {
    AppPreview {
        HomeGreeting(
            userName = "Sample text",
            onProfileClick = {},
        )
    }
}
