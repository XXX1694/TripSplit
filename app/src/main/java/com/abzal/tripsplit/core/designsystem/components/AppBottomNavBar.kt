package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing

class BottomNavItem(val label: String, val icon: ImageVector)

/** Bottom tabs of a trip: Overview, Expenses, Balances, Insights. */
@Composable
fun AppBottomNavBar(
    items: List<BottomNavItem>,
    selectedIndex: Int,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier, color = AppTheme.colors.surface) {
        Column(modifier = Modifier.navigationBarsPadding()) {
            AppDivider()
            Row(
                modifier = Modifier.fillMaxWidth().height(Sizes.bottomBar),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items.forEachIndexed { index, item ->
                    BottomNavTab(item, selected = index == selectedIndex, onClick = { onItemClick(index) })
                }
            }
        }
    }
}

@Composable
private fun BottomNavTab(item: BottomNavItem, selected: Boolean, onClick: () -> Unit) {
    val colors = AppTheme.colors
    val contentColor = if (selected) colors.primary else colors.textDisabled
    Column(
        modifier = Modifier.clickable(onClick = onClick).padding(horizontal = Spacing.sm),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(shape = CircleShape, color = if (selected) colors.primaryContainer else colors.surface) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.padding(horizontal = Spacing.md, vertical = 4.dp).size(Sizes.icon),
            )
        }
        Text(text = item.label, style = MaterialTheme.typography.labelMedium, color = contentColor)
    }
}
