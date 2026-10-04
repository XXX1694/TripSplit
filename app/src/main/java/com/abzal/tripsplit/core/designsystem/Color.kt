package com.abzal.tripsplit.core.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.abzal.tripsplit.core.designsystem.components.colors

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

    // Content on dark primary cards (hero cards)
    val onHero: Color,
    val onHeroMuted: Color,
    val heroOverlay: Color,
    val heroOutline: Color,
    val heroBar: Color,

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
    outline = Color(0xFFDDE5E1),
    divider = Color(0xFFE9EEEB),
    track = Color(0xFFE9EEEB),

    textPrimary = Color(0xFF18221F),
    textSecondary = Color(0xFF65726D),
    textDisabled = Color(0xFFA5ADA9),

    primary = Color(0xFF176B57),
    onPrimary = Color(0xFFFFFFFF),
    primaryDark = Color(0xFF0D4C3D),
    primaryContainer = Color(0xFFDCEFE8),

    onHero = Color(0xFFFFFFFF),
    onHeroMuted = Color(0xCCFFFFFF),
    heroOverlay = Color(0x26FFFFFF),
    heroOutline = Color(0x4DFFFFFF),
    heroBar = Color(0x59FFFFFF),

    positive = Color(0xFF17805E),
    negative = Color(0xFFC84B4B),
    negativeContainer = Color(0xFFFCE7E5),
    warning = Color(0xFFB87524),
    warningContainer = Color(0xFFFFF0E1),
    info = Color(0xFF356CB6),
    infoContainer = Color(0xFFE6EFFB),
)

/** Dark palette: canvas, surface, text, muted text, accent and border are from the designer's token sheet. */
val DarkAppColors = AppColors(
    background = Color(0xFF0B1513),
    surface = Color(0xFF12211E),
    outline = Color(0xFF29413B),
    divider = Color(0xFF223833),
    track = Color(0xFF223833),

    textPrimary = Color(0xFFF2FAF7),
    textSecondary = Color(0xFFA9BBB5),
    textDisabled = Color(0xFF7C918B),

    primary = Color(0xFF55D7B1),
    onPrimary = Color(0xFF0B1513),
    primaryDark = Color(0xFF0D4F40),
    primaryContainer = Color(0xFF173D34),

    onHero = Color(0xFFF2FAF7),
    onHeroMuted = Color(0xCCF2FAF7),
    heroOverlay = Color(0x26FFFFFF),
    heroOutline = Color(0x4DFFFFFF),
    heroBar = Color(0x59FFFFFF),

    positive = Color(0xFF58DDB7),
    negative = Color(0xFFFF8585),
    negativeContainer = Color(0xFF3A2222),
    warning = Color(0xFFE27A79),
    warningContainer = Color(0xFF3A2222),
    info = Color(0xFF88B7FF),
    infoContainer = Color(0xFF152B45),
)
