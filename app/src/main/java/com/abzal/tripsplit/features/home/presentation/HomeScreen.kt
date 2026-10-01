package com.abzal.tripsplit.features.home.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.designsystem.AppTheme
import com.abzal.tripsplit.core.designsystem.TripSplitTheme
import com.abzal.tripsplit.core.designsystem.components.AppFab
import com.abzal.tripsplit.core.designsystem.components.AppScaffold
import com.abzal.tripsplit.core.designsystem.components.AppTopBar
import com.abzal.tripsplit.core.designsystem.components.SectionHeader
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.home.presentation.components.ActiveTripCard
import com.abzal.tripsplit.features.home.presentation.components.HomeGreeting
import com.abzal.tripsplit.features.home.presentation.components.PastTripCard
import com.abzal.tripsplit.features.trips.domain.model.Trip

@Composable
fun HomeRoute(
    onTripClick: (String) -> Unit,
    onEditTripClick: (String) -> Unit,
    onCreateTripClick: () -> Unit,
    onJoinTripClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: HomeViewModel = injectedViewModel { c, _ ->
        HomeViewModel(c.authRepository, c.tripRepository, c.expenseRepository, c.participantRepository)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        onTripClick = onTripClick,
        onEditTripClick = onEditTripClick,
        onCreateTripClick = onCreateTripClick,
        onJoinTripClick = onJoinTripClick,
        onProfileClick = onProfileClick,
    )
}

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
            EmptyTrips()
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
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun EmptyTrips() {
    Text(
        text = "No trips yet. Create your first trip or join one with an invitation code.",
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyMedium,
        color = AppTheme.colors.textSecondary,
        textAlign = TextAlign.Center,
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    val past = Trip("2", "Kyoto Spring", "JPY", 1_743_000_000_000, 1_743_500_000_000)
    TripSplitTheme {
        HomeScreen(
            uiState = HomeUiState(
                userName = "Maya Kim",
                trips = listOf(
                    TripSummary(Trip("1", "Lisbon Friends 2026", "EUR"), listOf("Maya Kim", "Leo Evans", "Sam Adeyemi", "Nina Rossi"), 1284.6),
                    TripSummary(past, listOf("Maya Kim", "Leo Evans"), 218400.0),
                ),
            ),
            onTripClick = {},
            onEditTripClick = {},
            onCreateTripClick = {},
            onJoinTripClick = {},
            onProfileClick = {},
        )
    }
}
