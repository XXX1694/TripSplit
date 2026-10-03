package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.preview.AppPreview

/** Thin rounded bar. [progress] is from 0f to 1f. */
@Composable
fun AppProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colors.positive,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Sizes.progressBar)
            .clip(CircleShape)
            .background(AppTheme.colors.track),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .clip(CircleShape)
                .background(color),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppProgressBarPreview() {
    AppPreview {
        AppProgressBar(
            progress = 0.6f,
        )
    }
}
