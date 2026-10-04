package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppListRow
import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.core.designsystem.components.avatarToneAt
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.preview.sampleCurrencies
import com.abzal.tripsplit.features.expenses.domain.model.CurrencyInfo
import com.abzal.tripsplit.features.participants.presentation.components.tone

/** One currency: colored code badge, name, symbol and a check mark when selected. */
@Composable
fun CurrencyRow(
    currency: CurrencyInfo,
    toneIndex: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppListRow(
        title = currency.name,
        modifier = modifier,
        onClick = onClick,
        leading = { CurrencyCodeBadge(code = currency.code, tone = avatarToneAt(toneIndex)) },
        trailing = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                Text(
                    text = currency.symbol,
                    style = MaterialTheme.typography.titleMedium,
                    color = AppTheme.colors.textPrimary,
                )
                if (isSelected) {
                    Icon(Icons.Outlined.CheckCircle, contentDescription = "Selected", tint = AppTheme.colors.primary)
                } else {
                    Box(Modifier.width(Sizes.iconDefault))
                }
            }
        },
    )
}

@Composable
private fun CurrencyCodeBadge(code: String, tone: Tone) {
    val colors = tone.colors()
    Box(
        modifier = Modifier.size(Sizes.iconBadgeLarge).clip(MaterialTheme.shapes.medium).background(colors.container),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = code, style = MaterialTheme.typography.labelMedium, color = colors.content)
    }
}

@ThemePreviews
@Composable
private fun CurrencyRowPreview() {
    AppPreview {
        CurrencyRow(
            currency = sampleCurrencies.first(),
            toneIndex = 0,
            isSelected = true,
            onClick = {},
        )
    }
}
