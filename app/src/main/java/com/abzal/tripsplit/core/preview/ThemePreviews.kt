package com.abzal.tripsplit.core.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/** Shows a @Preview in both the light and the dark theme. */
@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class ThemePreviews
