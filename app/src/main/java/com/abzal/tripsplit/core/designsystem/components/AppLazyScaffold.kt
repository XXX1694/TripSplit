package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews

/**
 * [AppScaffold] for long lists: the content is a [LazyColumn], so only visible items are composed.
 * Use `item { }` and `items(list) { }` in [content]. Set [hasFab] to keep the last item above the floating button.
 */
@Composable
fun AppLazyScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    hasFab: Boolean = false,
    content: LazyListScope.() -> Unit,
) {
    Scaffold(
        modifier = modifier,
        containerColor = AppTheme.colors.canvas,
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(
                start = Spacing.screen,
                end = Spacing.screen,
                bottom = if (hasFab) Sizes.fabClearance else Spacing.none,
            ),
            verticalArrangement = Arrangement.spacedBy(Spacing.space16),
            content = content,
        )
    }
}

@ThemePreviews
@Composable
private fun AppLazyScaffoldPreview() {
    AppPreview {
        AppLazyScaffold(
            topBar = { AppTopBar(title = "List") },
            content = {
                items((1..12).toList()) { number -> Text("Item $number") }
            },
        )
    }
}
