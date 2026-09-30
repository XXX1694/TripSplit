package com.abzal.tripsplit.core.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/** Gaps between elements. */
object Spacing {
    val xxs = 4.dp
    val xs = 8.dp
    val sm = 12.dp
    val md = 16.dp
    val lg = 20.dp
    val xl = 24.dp
    val xxl = 32.dp

    /** Horizontal padding of every screen. */
    val screen = 18.dp
}

/** Fixed sizes of reusable elements. */
object Sizes {
    val button = 50.dp
    val field = 56.dp
    val iconBadge = 40.dp
    val avatar = 40.dp
    val avatarSmall = 28.dp
    val icon = 20.dp
    val bottomBar = 64.dp
    val progressBar = 6.dp
}

/** Corner radii: extraSmall - chips tags, small - badges, medium - buttons, large - fields, extraLarge - cards. */
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(22.dp),
)
