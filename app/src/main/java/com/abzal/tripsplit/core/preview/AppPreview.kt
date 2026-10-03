package com.abzal.tripsplit.core.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.colors

/** Wraps every @Preview: app theme and the app background. */
@Composable
fun AppPreview(content: @Composable () -> Unit) {
    TripSplitTheme(darkTheme = false) {
        Surface(color = AppTheme.colors.background, content = content)
    }
}
