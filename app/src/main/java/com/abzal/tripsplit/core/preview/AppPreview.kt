package com.abzal.tripsplit.core.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.colors

/** Wraps every @Preview: app theme (follows the preview's light / dark mode) and the app background. */
@Composable
fun AppPreview(content: @Composable () -> Unit) {
    TripSplitTheme {
        Surface(color = AppTheme.colors.canvas, content = content)
    }
}
