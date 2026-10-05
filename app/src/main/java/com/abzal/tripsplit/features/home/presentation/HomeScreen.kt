package com.abzal.tripsplit.features.home.presentation

import androidx.compose.material.icons.outlined.Luggage
import com.abzal.tripsplit.core.designsystem.components.EmptyState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abzal.tripsplit.core.designsystem.Sizes
import com.abzal.tripsplit.core.designsystem.components.AppFab
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.preview.AppPreview
import com.abzal.tripsplit.core.preview.ThemePreviews
import com.abzal.tripsplit.features.home.presentation.components.ActiveTripCard
import com.abzal.tripsplit.features.home.presentation.components.HomeGreeting
import com.abzal.tripsplit.features.home.presentation.components.PastTripCard

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onTripClick: (String) -> Unit,
    onEditTripClick: (String) -> Unit,
    onCreateTripClick: () -> Unit,
    onJoinTripClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    val activeTrip = uiState.activeTrip
    val pastTrips = uiState.pastTrips

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Your trips",
                subtitle = "Shared adventures, settled simply",
                actions = {
                    IconButton(onClick = onJoinTripClick) {
                        Icon(Icons.Outlined.GroupAdd, contentDescription = "Join a trip")
                    }
                },
            )
        },
        floatingActionButton = {
            AppFab(text = "Create trip", icon = Icons.Outlined.Add, onClick = onCreateTripClick)
        },
    ) {
        HomeGreeting(userName = uiState.userName, onProfileClick = onProfileClick)

        if (uiState.trips.isEmpty()) {
            EmptyState(
                title = "No trips yet",
                message = "Create your first trip or join one with an invitation code.",
                icon = Icons.Outlined.Luggage,
                actionText = "Create trip",
                onActionClick = onCreateTripClick,
            )
        }
        if (activeTrip != null) {
            SectionHeader(title = "Active trip")
            ActiveTripCard(
                summary = activeTrip,
                onClick = { onTripClick(activeTrip.trip.id) },
                onMoreClick = { onEditTripClick(activeTrip.trip.id) },
            )
        }
        if (pastTrips.isNotEmpty()) {
            SectionHeader(title = "Past trips")
            pastTrips.forEach { summary ->
                PastTripCard(
                    summary = summary,
                    onClick = { onTripClick(summary.trip.id) },
                    onMoreClick = { onEditTripClick(summary.trip.id) },
                )
            }
        }
        // keeps the last card above the floating button
        Spacer(Modifier.height(Sizes.fabClearance))
    }
}

@ThemePreviews
@Composable
private fun HomeScreenPreview() {
    AppPreview {
        HomeScreen(
            uiState = sampleHomeUiState,
            onTripClick = {},
            onEditTripClick = {},
            onCreateTripClick = {},
            onJoinTripClick = {},
            onProfileClick = {},
        )
    }
}
