package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.abzal.tripsplit.core.designsystem.AppTheme

/** Color accent used by avatars, badges, banners and pills. */
enum class Tone { Primary, Info, Warning, Negative }

class ToneColors(val container: Color, val content: Color)

@Composable
fun Tone.colors(): ToneColors {
    val colors = AppTheme.colors
    return when (this) {
        Tone.Primary -> ToneColors(colors.primaryContainer, colors.primary)
        Tone.Info -> ToneColors(colors.infoContainer, colors.info)
        Tone.Warning -> ToneColors(colors.warningContainer, colors.warning)
        Tone.Negative -> ToneColors(colors.negativeContainer, colors.negative)
    }
}
