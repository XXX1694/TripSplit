package com.abzal.tripsplit.core.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.abzal.tripsplit.core.navigation.Routes
import com.abzal.tripsplit.core.navigation.pickedCurrency
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.home.presentation.HomeRoute
import com.abzal.tripsplit.features.participants.presentation.JoinTripRoute
import com.abzal.tripsplit.features.trips.presentation.CreateTripRoute
import com.abzal.tripsplit.features.trips.presentation.DeleteTripRoute
import com.abzal.tripsplit.features.trips.presentation.EditTripRoute

fun NavGraphBuilder.tripsGraph(navController: NavHostController) {
        composable(Routes.HOME) {
            HomeRoute(
                onTripClick = { navController.navigate(Routes.tripOverview(it)) },
                onEditTripClick = { navController.navigate(Routes.editTrip(it)) },
                onCreateTripClick = { navController.navigate(Routes.CREATE_TRIP) },
                onJoinTripClick = { navController.navigate(Routes.JOIN_TRIP) },
                onProfileClick = { navController.navigate(Routes.PROFILE) },
            )
        }
        composable(Routes.CREATE_TRIP) { entry ->
            CreateTripRoute(
                onBackClick = { navController.popBackStack() },
                onCreated = { tripId ->
                    navController.navigate(Routes.tripOverview(tripId)) {
                        popUpTo(Routes.CREATE_TRIP) { inclusive = true }
                    }
                },
                onPickCurrencyClick = { navController.navigate(Routes.CURRENCY_PICKER) },
                pickedCurrency = entry.pickedCurrency(),
            )
        }
        composable(Routes.JOIN_TRIP) {
            JoinTripRoute(
                onBackClick = { navController.popBackStack() },
                onJoined = { tripId ->
                    navController.navigate(Routes.tripOverview(tripId)) {
                        popUpTo(Routes.JOIN_TRIP) { inclusive = true }
                    }
                },
            )
        }
        composable(Routes.EDIT_TRIP) { entry ->
            val tripId = entry.tripId()
            EditTripRoute(
                onBackClick = { navController.popBackStack() },
                onSaved = { navController.popBackStack() },
                onDeleteClick = { navController.navigate(Routes.deleteTrip(tripId)) },
                onPickCurrencyClick = { navController.navigate(Routes.CURRENCY_PICKER) },
                pickedCurrency = entry.pickedCurrency(),
            )
        }
        composable(Routes.DELETE_TRIP) {
            DeleteTripRoute(
                onCancelClick = { navController.popBackStack() },
                onDeleted = { navController.popBackStack(Routes.HOME, inclusive = false) },
            )
        }
}
