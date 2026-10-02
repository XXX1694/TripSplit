package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Balance
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

enum class TripTab(val label: String, val icon: ImageVector) {
    Overview("Overview", Icons.Outlined.Dashboard),
    Expenses("Expenses", Icons.Outlined.Receipt),
    Balances("Balances", Icons.Outlined.Balance),
    Insights("Insights", Icons.Outlined.PieChart),
}

/** Bottom tabs shared by all main screens of a trip. */
@Composable
fun TripBottomBar(
    selected: TripTab,
    onTabClick: (TripTab) -> Unit,
) {
    AppBottomNavBar(
        items = TripTab.entries.map { BottomNavItem(it.label, it.icon) },
        selectedIndex = selected.ordinal,
        onItemClick = { onTabClick(TripTab.entries[it]) },
    )
}
