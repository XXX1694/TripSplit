package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.preview.AppPreview

/**
 * Filter chip that opens a menu of [options]. Shows [title] until something is [selected].
 * [onSelect] gets null when the user picks "Any".
 */
@Composable
fun AppDropdownChip(
    title: String,
    options: List<String>,
    selected: String?,
    onSelect: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isOpen by remember { mutableStateOf(false) }

    AppChip(
        text = selected ?: title,
        selected = selected != null,
        icon = Icons.Outlined.KeyboardArrowDown,
        onClick = { isOpen = true },
        modifier = modifier,
    )
    DropdownMenu(expanded = isOpen, onDismissRequest = { isOpen = false }) {
        DropdownMenuItem(text = { Text("Any") }, onClick = { isOpen = false; onSelect(null) })
        options.forEach { option ->
            DropdownMenuItem(text = { Text(option) }, onClick = { isOpen = false; onSelect(option) })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppDropdownChipPreview() {
    AppPreview {
        AppDropdownChip(
            title = "Title",
            options = listOf("Food", "Stay", "Transit"),
            selected = "Food",
            onSelect = {},
        )
    }
}
