package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme

class BarValue(val label: String, val value: Double, val isHighlighted: Boolean = false)

/** Vertical bars with a label under each. Colors are parameters so it also works on a dark card. */
@Composable
fun AppBarChart(
    values: List<BarValue>,
    barColor: Color,
    highlightColor: Color,
    labelColor: Color,
    modifier: Modifier = Modifier,
    chartHeight: Dp = 90.dp,
) {
    val max = values.maxOfOrNull { it.value }?.takeIf { it > 0 } ?: 1.0
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
    ) {
        values.forEach { bar ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height((chartHeight.value * (bar.value / max)).dp.coerceAtLeast(6.dp))
                        .background(if (bar.isHighlighted) highlightColor else barColor, RoundedCornerShape(8.dp)),
                )
                Text(bar.label, style = MaterialTheme.typography.bodySmall, color = labelColor)
            }
        }
    }
}

/** Ring split into parts by [BarSegment.weight]; [center] is drawn in the middle. */
@Composable
fun AppDonutChart(
    segments: List<BarSegment>,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    strokeWidth: Dp = 18.dp,
    center: @Composable () -> Unit = {},
) {
    val trackColor = AppTheme.colors.track
    val total = segments.sumOf { it.weight.toDouble() }.toFloat()

    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(size)) {
            val stroke = strokeWidth.toPx()
            val arcSize = Size(this.size.width - stroke, this.size.height - stroke)
            val topLeft = Offset(stroke / 2, stroke / 2)

            drawArc(trackColor, 0f, 360f, useCenter = false, topLeft = topLeft, size = arcSize, style = Stroke(stroke))
            var startAngle = -90f
            if (total > 0f) {
                segments.forEach { segment ->
                    val sweep = 360f * segment.weight / total
                    drawArc(
                        color = segment.color,
                        startAngle = startAngle,
                        sweepAngle = (sweep - 2f).coerceAtLeast(0f),
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(stroke, cap = StrokeCap.Butt),
                    )
                    startAngle += sweep
                }
            }
        }
        center()
    }
}
