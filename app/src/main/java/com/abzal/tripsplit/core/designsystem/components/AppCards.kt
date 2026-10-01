package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing

/** White rounded card that groups related content. Pass [onClick] to make the whole card clickable. */
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    contentPadding: Dp = Spacing.md,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(Spacing.sm),
    content: @Composable ColumnScope.() -> Unit,
) {
    val cardModifier = modifier.fillMaxWidth()
    val shape = MaterialTheme.shapes.extraLarge
    val color = AppTheme.colors.surface
    val cardContent: @Composable () -> Unit = {
        Column(
            modifier = Modifier.padding(contentPadding),
            verticalArrangement = verticalArrangement,
            content = content,
        )
    }
    if (onClick != null) {
        Surface(onClick = onClick, modifier = cardModifier, shape = shape, color = color, shadowElevation = 2.dp, content = cardContent)
    } else {
        Surface(modifier = cardModifier, shape = shape, color = color, shadowElevation = 2.dp, content = cardContent)
    }
}

/** Dark green card for the main figure of a screen (total spending, net balance, profile). */
@Composable
fun HeroCard(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(Spacing.sm),
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        color = AppTheme.colors.primaryDark,
        contentColor = AppTheme.colors.onPrimary,
    ) {
        Column(
            modifier = Modifier.padding(Spacing.lg),
            verticalArrangement = verticalArrangement,
            content = content,
        )
    }
}
