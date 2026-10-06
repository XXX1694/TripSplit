package com.abzal.tripsplit.core.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/** The spacing scale from the design: 4, 8, 12, 16, 24, 32 dp. */
object Spacing {
    val none = 0.dp
    val space4 = 4.dp
    val space8 = 8.dp
    val space12 = 12.dp
    val space16 = 16.dp
    val space24 = 24.dp
    val space32 = 32.dp

    /** Horizontal padding of every screen. */
    val screen = space16
}

/** Fixed sizes of reusable elements. */
object Sizes {
    /** Minimum size of everything that can be tapped. */
    val touchTarget = 48.dp

    val button = 50.dp
    val field = 56.dp
    val bottomBar = 64.dp

    /** Empty space under the last item so the floating button does not cover it. */
    val fabClearance = 80.dp

    // icons
    val iconTiny = 14.dp
    val iconSmall = 18.dp
    val icon = 20.dp
    val iconDefault = 24.dp
    val iconLarge = 32.dp

    // badges and avatars
    val dot = 8.dp
    val iconBadge = 40.dp
    val iconBadgeLarge = 48.dp
    val bigBadge = 68.dp
    val avatarSmall = 28.dp
    val avatar = 40.dp
    val avatarMedium = 48.dp
    val avatarLarge = 56.dp

    // images
    val coverCard = 82.dp
    val coverBanner = 120.dp
    val coverThumb = 56.dp
    val coverSide = 104.dp

    // progress and charts
    val progressBar = 6.dp
    val progressRing = 64.dp
    val segmentBar = 8.dp
    val chartBar = 32.dp
    val chartHeight = 90.dp
    val donut = 120.dp

    // stepper
    val stepCircle = 32.dp
    val stepLabelWidth = 72.dp
}

object Strokes {
    val thin = 1.dp
    val thick = 2.dp
    val donut = 18.dp
}

object Elevations {
    val card = 2.dp
    val fab = 4.dp
}

/** Corner radii: extraSmall - chips tags, small - badges, medium - buttons, large - fields, extraLarge - cards. */
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(22.dp),
)
