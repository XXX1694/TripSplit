package com.abzal.tripsplit.core.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.abzal.tripsplit.R
import com.abzal.tripsplit.features.participants.presentation.components.label

val InterFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold),
)

private fun style(size: Int, lineHeight: Int, weight: FontWeight, letterSpacing: Double = 0.0) = TextStyle(
    fontFamily = InterFamily,
    fontSize = size.sp,
    lineHeight = lineHeight.sp,
    fontWeight = weight,
    letterSpacing = letterSpacing.sp,
)

/**
 * Text styles from the design:
 *  display  - big amounts
 *  headline - screen titles ("Welcome back")
 *  title    - top bar and card titles
 *  body     - regular text
 *  label    - buttons, chips, captions, uppercase overlines
 *
 * Every Material style is listed so no text falls back to the system font: the whole app uses Inter.
 */
val AppTypography = Typography(
    displayLarge = style(48, 56, FontWeight.Medium),
    displayMedium = style(40, 46, FontWeight.Medium),
    displaySmall = style(32, 38, FontWeight.Medium),
    headlineLarge = style(28, 34, FontWeight.Medium),
    headlineMedium = style(24, 30, FontWeight.Medium),
    headlineSmall = style(22, 28, FontWeight.Medium),
    titleLarge = style(20, 26, FontWeight.Medium),
    titleMedium = style(16, 22, FontWeight.Medium),
    titleSmall = style(14, 20, FontWeight.SemiBold),
    bodyLarge = style(16, 24, FontWeight.Normal),
    bodyMedium = style(14, 20, FontWeight.Normal),
    bodySmall = style(12, 16, FontWeight.Normal),
    labelLarge = style(15, 20, FontWeight.SemiBold),
    labelMedium = style(12, 16, FontWeight.Medium),
    labelSmall = style(11, 14, FontWeight.Medium, letterSpacing = 0.6),
)
