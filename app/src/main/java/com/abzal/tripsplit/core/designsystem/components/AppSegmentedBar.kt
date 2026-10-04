package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Strokes
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.preview.sampleBarSegments

/** Bar split into colored parts. Each part is a [weight] (any positive number) and a [color]. */
class BarSegment(val weight: Float, val color: Color)

@Composable
fun AppSegmentedBar(
    segments: List<BarSegment>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Strokes.thick)) {
        segments.filter { it.weight > 0f }.forEach { segment ->
            Box(
                modifier = Modifier
                    .weight(segment.weight)
                    .height(Sizes.segmentBar)
                    .clip(CircleShape)
                    .background(segment.color),
            )
        }
    }
}

@ThemePreviews
@Composable
private fun AppSegmentedBarPreview() {
    AppPreview {
        AppSegmentedBar(
            segments = sampleBarSegments(),
        )
    }
}
