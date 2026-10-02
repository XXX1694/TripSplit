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
import androidx.compose.ui.unit.dp

/** Bar split into colored parts. Each part is a [weight] (any positive number) and a [color]. */
class BarSegment(val weight: Float, val color: Color)

@Composable
fun AppSegmentedBar(
    segments: List<BarSegment>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        segments.filter { it.weight > 0f }.forEach { segment ->
            Box(
                modifier = Modifier
                    .weight(segment.weight)
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(segment.color),
            )
        }
    }
}
