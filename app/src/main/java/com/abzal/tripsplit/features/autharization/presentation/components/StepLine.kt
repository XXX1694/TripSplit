package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Strokes
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview

@Composable
fun StepLine(isActive: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(top = (Sizes.stepCircle - Strokes.thick) / 2)
            .height(Strokes.thick)
            .background(if (isActive) AppTheme.colors.primary else AppTheme.colors.track),
    )
}

@Preview(showBackground = true)
@Composable
private fun StepLinePreview() {
    AppPreview {
        StepLine(
            isActive = true,
        )
    }
}
