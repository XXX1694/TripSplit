package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.Strokes
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.currencySymbol

/** Big bordered card: currency chip on the left, amount input on the right. */
@Composable
fun AmountCard(
    amountText: String,
    currency: String,
    onAmountChange: (String) -> Unit,
    onCurrencyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        color = colors.surface,
        border = BorderStroke(Strokes.thick, colors.accent),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                onClick = onCurrencyClick,
                modifier = Modifier.heightIn(min = Sizes.touchTarget),
                shape = CircleShape,
                color = colors.accentSoft,
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = Spacing.sm, vertical = Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(currency, style = MaterialTheme.typography.labelLarge, color = colors.accent)
                    Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null, tint = colors.accent, modifier = Modifier.size(Sizes.iconSmall))
                }
            }
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                Overline("Amount")
                AmountInput(amountText, currencySymbol(currency), onAmountChange)
            }
        }
    }
}

@Composable
private fun AmountInput(text: String, symbol: String, onChange: (String) -> Unit) {
    val colors = AppTheme.colors
    val style = MaterialTheme.typography.headlineLarge.copy(
        color = if (text.isEmpty()) colors.textDisabled else colors.text,
        textAlign = TextAlign.End,
    )
    BasicTextField(
        value = text,
        onValueChange = onChange,
        singleLine = true,
        textStyle = style,
        cursorBrush = SolidColor(colors.accent),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        decorationBox = { inner ->
            Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                Text(symbol, style = style.copy(color = colors.textDisabled))
                Box(contentAlignment = Alignment.CenterEnd) {
                    if (text.isEmpty()) Text("0.00", style = style)
                    inner()
                }
            }
        },
    )
}

@ThemePreviews
@Composable
private fun AmountCardPreview() {
    AppPreview {
        AmountCard(
            amountText = "104.20",
            currency = "EUR",
            onAmountChange = {},
            onCurrencyClick = {},
        )
    }
}
