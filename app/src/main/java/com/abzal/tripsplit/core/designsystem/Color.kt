package com.abzal.tripsplit.core.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.abzal.tripsplit.core.designsystem.components.colors

/** All colors used by the app. Screens read them via [AppTheme.colors]. */
@Immutable
class AppColors(
    // Surfaces (canvas = screen background)
    val canvas: Color,
    val surface: Color,
    val border: Color,
    val divider: Color,
    val track: Color,

    // Text
    val text: Color,
    val textMuted: Color,
    val textDisabled: Color,

    // Brand
    val accent: Color,
    val onAccent: Color,
    val accentDark: Color,
    val accentSoft: Color,

    // Content on dark primary cards (hero cards)
    val onHero: Color,
    val onHeroMuted: Color,
    val heroOverlay: Color,
    val heroOutline: Color,
    val heroBar: Color,

    // Money / status
    val positive: Color,
    val danger: Color,
    val dangerContainer: Color,
    val warning: Color,
    val warningContainer: Color,
    val info: Color,
    val infoContainer: Color,
)

val LightAppColors = AppColors(
    canvas = Color(0xFFF5F7F5),
    surface = Color(0xFFFFFFFF),
    border = Color(0xFFDDE5E1),
    divider = Color(0xFFE9EEEB),
    track = Color(0xFFE9EEEB),

    text = Color(0xFF18221F),
    textMuted = Color(0xFF65726D),
    textDisabled = Color(0xFFA5ADA9),

    accent = Color(0xFF176B57),
    onAccent = Color(0xFFFFFFFF),
    accentDark = Color(0xFF0D4C3D),
    accentSoft = Color(0xFFDCEFE8),

    onHero = Color(0xFFFFFFFF),
    onHeroMuted = Color(0xCCFFFFFF),
    heroOverlay = Color(0x26FFFFFF),
    heroOutline = Color(0x4DFFFFFF),
    heroBar = Color(0x59FFFFFF),

    positive = Color(0xFF17805E),
    danger = Color(0xFFC84B4B),
    dangerContainer = Color(0xFFFCE7E5),
    warning = Color(0xFFB87524),
    warningContainer = Color(0xFFFFF0E1),
    info = Color(0xFF356CB6),
    infoContainer = Color(0xFFE6EFFB),
)

/** Dark palette: canvas, surface, text, muted text, accent and border are from the designer's token sheet. */
val DarkAppColors = AppColors(
    canvas = Color(0xFF0B1513),
    surface = Color(0xFF12211E),
    border = Color(0xFF29413B),
    divider = Color(0xFF223833),
    track = Color(0xFF223833),

    text = Color(0xFFF2FAF7),
    textMuted = Color(0xFFA9BBB5),
    textDisabled = Color(0xFF7C918B),

    accent = Color(0xFF55D7B1),
    onAccent = Color(0xFF0B1513),
    accentDark = Color(0xFF0D4F40),
    accentSoft = Color(0xFF173D34),

    onHero = Color(0xFFF2FAF7),
    onHeroMuted = Color(0xCCF2FAF7),
    heroOverlay = Color(0x26FFFFFF),
    heroOutline = Color(0x4DFFFFFF),
    heroBar = Color(0x59FFFFFF),

    positive = Color(0xFF58DDB7),
    danger = Color(0xFFFF8585),
    dangerContainer = Color(0xFF3A2222),
    warning = Color(0xFFE27A79),
    warningContainer = Color(0xFF3A2222),
    info = Color(0xFF88B7FF),
    infoContainer = Color(0xFF152B45),
)
