package com.abzal.tripsplit.features.autharization.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.BigIconBadge
import com.abzal.tripsplit.core.designsystem.components.StatusPill
import com.abzal.tripsplit.core.designsystem.components.colors
import com.abzal.tripsplit.core.preview.AppPreview

@Composable
fun ResetSentHeader(email: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        BigIconBadge(icon = Icons.Outlined.MarkEmailRead)
        StatusPill(text = "Email sent", icon = Icons.Outlined.CheckCircle)
        Text(
            text = "Check your inbox",
            style = MaterialTheme.typography.headlineLarge,
            color = AppTheme.colors.textPrimary,
        )
        Text(
            text = buildAnnotatedString {
                append("We sent a secure reset link to ")
                withStyle(SpanStyle(fontWeight = FontWeight.SemiBold, color = AppTheme.colors.textPrimary)) { append(email) }
                append(".")
            },
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResetSentHeaderPreview() {
    AppPreview {
        ResetSentHeader(
            email = "maya@hey.com",
        )
    }
}
