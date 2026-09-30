package com.abzal.tripsplit.core.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/** All colors used by the app. Screens read them via [AppTheme.colors]. */
@Immutable
class AppColors(
    // Surfaces
    val background: Color,
    val surface: Color,
    val outline: Color,
    val divider: Color,
    val track: Color,

    // Text
    val textPrimary: Color,
    val textSecondary: Color,
    val textDisabled: Color,

    // Brand
    val primary: Color,
    val onPrimary: Color,
    val primaryDark: Color,
    val primaryContainer: Color,

    // Money / status
    val positive: Color,
    val negative: Color,
    val negativeContainer: Color,
    val warning: Color,
    val warningContainer: Color,
    val info: Color,
    val infoContainer: Color,
)

val LightAppColors = AppColors(
    background = Color(0xFFF5F7F5),
    surface = Color(0xFFFFFFFF),
    outline = Color(0xFFDCE3DF),
    divider = Color(0xFFE9EEEB),
    track = Color(0xFFE9EEEB),

    textPrimary = Color(0xFF1B2420),
    textSecondary = Color(0xFF6B7570),
    textDisabled = Color(0xFFA5ADA9),

    primary = Color(0xFF176B57),
    onPrimary = Color(0xFFFFFFFF),
    primaryDark = Color(0xFF0D4C3D),
    primaryContainer = Color(0xFFDCEFE8),

    positive = Color(0xFF17805E),
    negative = Color(0xFFC84B4B),
    negativeContainer = Color(0xFFFCE7E5),
    warning = Color(0xFFB87524),
    warningContainer = Color(0xFFFFF0E1),
    info = Color(0xFF356CB6),
    infoContainer = Color(0xFFE6EFFB),
)

// TODO: dark palette is not decided yet. Until then dark theme reuses the light colors.
val DarkAppColors = LightAppColors
