package com.abzal.tripsplit.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.abzal.tripsplit.R
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews

/** Trip cover keys (stored in `Trip.cover`) and the photos from resources they point to. */
@DrawableRes
fun coverImageRes(cover: String?): Int? = when (cover) {
    "lisbon" -> R.drawable.cover_lisbon
    "kyoto" -> R.drawable.cover_kyoto
    "alps" -> R.drawable.cover_alps
    else -> null
}

/**
 * Trip photo from resources, cropped to fill the given size.
 * Without an [imageRes] a neutral placeholder is shown. [contentDescription] tells screen readers what the photo is.
 */
@Composable
fun CoverImage(
    contentDescription: String,
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int? = null,
) {
    if (imageRes != null) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop,
        )
    } else {
        Box(modifier = modifier.background(AppTheme.colors.accentSoft), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Outlined.Landscape,
                contentDescription = contentDescription,
                tint = AppTheme.colors.accent,
                modifier = Modifier.size(Sizes.iconLarge),
            )
        }
    }
}

@ThemePreviews
@Composable
private fun CoverImagePreview() {
    AppPreview {
        CoverImage(
            contentDescription = "Cover photo of Lisbon Friends 2026",
            imageRes = R.drawable.cover_lisbon,
            modifier = Modifier.size(Sizes.coverBanner),
        )
    }
}

@ThemePreviews
@Composable
private fun CoverImagePlaceholderPreview() {
    AppPreview {
        CoverImage(
            contentDescription = "Cover photo is not set",
            modifier = Modifier.size(Sizes.coverBanner),
        )
    }
}
