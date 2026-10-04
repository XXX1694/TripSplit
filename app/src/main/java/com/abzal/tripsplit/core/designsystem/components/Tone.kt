package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.abzal.tripsplit.core.designsystem.AppTheme

/** Color accent used by avatars, badges, banners and pills. */
enum class Tone { Accent, Info, Warning, Danger }

class ToneColors(val container: Color, val content: Color)

@Composable
fun Tone.colors(): ToneColors {
    val colors = AppTheme.colors
    return when (this) {
        Tone.Accent -> ToneColors(colors.accentSoft, colors.accent)
        Tone.Info -> ToneColors(colors.infoContainer, colors.info)
        Tone.Warning -> ToneColors(colors.warningContainer, colors.warning)
        Tone.Danger -> ToneColors(colors.dangerContainer, colors.danger)
    }
}
