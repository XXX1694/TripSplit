package com.abzal.tripsplit.features.expenses.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.components.Overline
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.features.expenses.presentation.ExpenseDraft
import com.abzal.tripsplit.features.expenses.presentation.sampleExpenseDraft

@Composable
fun SplitHeader(draft: ExpenseDraft) {
    val count = draft.participantIds.size
    val share = draft.amount?.takeIf { count > 0 }?.let { "%.2f".format(it / count) }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Overline("Split between")
        Text(
            text = "Equally · " + (share?.let { "${draft.currency} $it each" } ?: "$count people"),
            style = MaterialTheme.typography.labelMedium,
            color = AppTheme.colors.positive,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplitHeaderPreview() {
    AppPreview {
        SplitHeader(
            draft = sampleExpenseDraft,
        )
    }
}
