package com.abzal.tripsplit.features.trips.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Luggage
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.Spacing
import com.abzal.tripsplit.core.designsystem.components.AppDateField
import com.abzal.tripsplit.core.designsystem.components.AppSelectField
import com.abzal.tripsplit.core.designsystem.components.AppTextField
import com.abzal.tripsplit.core.designsystem.components.CoverImage
import com.abzal.tripsplit.core.designsystem.components.InfoBanner
import com.abzal.tripsplit.core.designsystem.components.RowValue
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.core.util.currencyName
import com.abzal.tripsplit.core.util.currencySymbol
import com.abzal.tripsplit.features.participants.presentation.components.label
import com.abzal.tripsplit.features.trips.domain.model.Trip
import com.abzal.tripsplit.features.trips.domain.model.TripDraft
import com.abzal.tripsplit.features.trips.presentation.sampleTripDraft

/** Fields shared by the create and edit trip screens. */
@Composable
fun TripFormFields(
    draft: TripDraft,
    onDraftChange: (TripDraft) -> Unit,
    onPickCurrencyClick: () -> Unit,
    modifier: Modifier = Modifier,
    infoText: String = "You can add expenses in any currency. Conversion rates are saved per expense.",
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Spacing.space12)) {
        CoverImage(modifier = Modifier.fillMaxWidth().height(Sizes.coverBanner).clip(MaterialTheme.shapes.extraLarge))
        AppTextField(
            value = draft.name,
            onValueChange = { onDraftChange(draft.copy(name = it)) },
            label = "Trip name",
            leadingIcon = Icons.Outlined.Luggage,
        )
        AppTextField(
            value = draft.destination,
            onValueChange = { onDraftChange(draft.copy(destination = it)) },
            label = "Destination",
            leadingIcon = Icons.Outlined.Place,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.space12)) {
            AppDateField(
                label = "Starts",
                dateMillis = draft.startDateMillis,
                onDateSelected = { onDraftChange(draft.copy(startDateMillis = it)) },
                leadingIcon = Icons.Outlined.CalendarToday,
                modifier = Modifier.weight(1f),
            )
            AppDateField(
                label = "Ends",
                dateMillis = draft.endDateMillis,
                onDateSelected = { onDraftChange(draft.copy(endDateMillis = it)) },
                leadingIcon = Icons.Outlined.EventAvailable,
                modifier = Modifier.weight(1f),
            )
        }
        AppSelectField(
            label = "Base currency",
            value = currencyName(draft.currency),
            caption = "Balances are shown in ${draft.currency}",
            leadingIcon = Icons.Outlined.AttachMoney,
            onClick = onPickCurrencyClick,
            trailing = { RowValue("${draft.currency}  ${currencySymbol(draft.currency)}") },
        )
        InfoBanner(
            text = infoText,
            icon = Icons.Outlined.Info,
        )
    }
}

@ThemePreviews
@Composable
private fun TripFormFieldsPreview() {
    AppPreview {
        TripFormFields(
            draft = sampleTripDraft,
            onDraftChange = {},
            onPickCurrencyClick = {},
        )
    }
}
