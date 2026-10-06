package com.abzal.tripsplit.core.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.abzal.tripsplit.R

val InterFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold),
)

/** Every role is Inter with 0% letter spacing; line height is Auto unless a role says otherwise. */
private fun role(size: Int, weight: FontWeight, lineHeight: TextUnit = TextUnit.Unspecified) = TextStyle(
    fontFamily = InterFamily,
    fontSize = size.sp,
    fontWeight = weight,
    lineHeight = lineHeight,
    letterSpacing = 0.sp,
)

// ---- Roles from the designer's typography sheet (SplitTrip_typography.png) ----

private val AmountLarge = role(32, FontWeight.Normal)      // total spending
private val HeadingLarge = role(28, FontWeight.Medium)     // main heading, greeting
private val AmountMedium = role(28, FontWeight.Normal)     // key balance, summary
private val HeadingMedium = role(24, FontWeight.Medium)    // section and form headings
private val AmountSmall = role(24, FontWeight.Normal)      // amount in details and cards
private val TitleAppBar = role(20, FontWeight.Medium)      // top bar title
private val TitleRegular = role(20, FontWeight.Normal)     // trip name, screen title
private val TitleProfile = role(18, FontWeight.Medium)     // name in the profile
private val BodyLarge = role(16, FontWeight.Normal)        // large body text
private val TitleCard = role(16, FontWeight.Medium)        // card or block title
private val BodyMedium = role(14, FontWeight.Normal)       // main text, list rows
private val LabelButton = role(14, FontWeight.SemiBold)    // main action, button
private val LabelMedium = role(14, FontWeight.Medium)      // field label, action, filter
private val BodySmall = role(13, FontWeight.Normal)        // hints, notices
private val LabelLink = role(13, FontWeight.SemiBold)      // text link
private val CaptionMedium = role(12, FontWeight.Normal)    // indicator label
private val CaptionSmall = role(11, FontWeight.Normal)     // metadata
private val LabelBadge = role(11, FontWeight.SemiBold)     // status, badge
private val Micro = role(10, FontWeight.Normal)            // app version, fine print

// Line height for multi-line text (the sheet lists these separately from Auto).
private val BodySmallMultiline = role(13, FontWeight.Normal, lineHeight = 18.2.sp)     // 140%
private val CaptionMediumMultiline = role(12, FontWeight.Normal, lineHeight = 16.2.sp) // 135%
private val CaptionSmallMultiline = role(11, FontWeight.Normal, lineHeight = 14.85.sp) // 135%

/**
 * Material slots configured with the SplitTrip scale (not the standard Material 3 sizes):
 *  display  - amounts            headline - headings and the profile name
 *  title    - app bar and cards  body     - regular text
 *  label    - buttons, fields, badges
 * Roles that do not fit a Material slot are extension properties below.
 */
val AppTypography = Typography(
    displayLarge = AmountLarge,
    displayMedium = AmountMedium,
    displaySmall = AmountSmall,
    headlineLarge = HeadingLarge,
    headlineMedium = HeadingMedium,
    headlineSmall = TitleProfile,
    titleLarge = TitleAppBar,
    titleMedium = TitleCard,
    titleSmall = LabelMedium,
    bodyLarge = BodyLarge,
    bodyMedium = BodyMedium,
    bodySmall = BodySmall,
    labelLarge = LabelButton,
    labelMedium = LabelMedium,
    labelSmall = LabelBadge,
)

val Typography.titleRegular: TextStyle get() = TitleRegular

val Typography.labelLink: TextStyle get() = LabelLink

val Typography.captionMedium: TextStyle get() = CaptionMedium

val Typography.captionSmall: TextStyle get() = CaptionSmall

val Typography.micro: TextStyle get() = Micro

val Typography.bodySmallMultiline: TextStyle get() = BodySmallMultiline

val Typography.captionMediumMultiline: TextStyle get() = CaptionMediumMultiline

val Typography.captionSmallMultiline: TextStyle get() = CaptionSmallMultiline
