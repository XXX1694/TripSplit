package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.participants.presentation.components.label

class MenuItem(val label: String, val onClick: () -> Unit)

/** The "⋮" button with a menu. Put it into the `actions` of [AppTopBar]. */
@Composable
fun AppOverflowMenu(items: List<MenuItem>) {
    var isOpen by remember { mutableStateOf(false) }

    IconButton(onClick = { isOpen = true }) {
        Icon(Icons.Outlined.MoreVert, contentDescription = "More")
    }
    DropdownMenu(expanded = isOpen, onDismissRequest = { isOpen = false }) {
        items.forEach { item ->
            DropdownMenuItem(
                text = { Text(item.label) },
                onClick = {
                    isOpen = false
                    item.onClick()
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppOverflowMenuPreview() {
    AppPreview {
        AppOverflowMenu(
            items = listOf(MenuItem("Edit") {}, MenuItem("Delete") {}),
        )
    }
}
