package com.abzal.tripsplit.core.designsystem.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.abzal.tripsplit.core.util.formatDate

/** [AppSelectField] that opens a date picker. [dateMillis] is null while no date is chosen. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDateField(
    label: String,
    dateMillis: Long?,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
) {
    var isPickerOpen by remember { mutableStateOf(false) }

    AppSelectField(
        label = label,
        value = dateMillis?.let(::formatDate) ?: "Select date",
        isPlaceholder = dateMillis == null,
        leadingIcon = leadingIcon,
        onClick = { isPickerOpen = true },
        modifier = modifier,
    )

    if (isPickerOpen) {
        val pickerState = rememberDatePickerState(initialSelectedDateMillis = dateMillis)
        DatePickerDialog(
            onDismissRequest = { isPickerOpen = false },
            confirmButton = {
                AppTextButton(
                    text = "OK",
                    onClick = {
                        pickerState.selectedDateMillis?.let(onDateSelected)
                        isPickerOpen = false
                    },
                )
            },
            dismissButton = { AppTextButton(text = "Cancel", onClick = { isPickerOpen = false }) },
        ) {
            DatePicker(state = pickerState)
        }
    }
}
