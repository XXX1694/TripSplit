package com.abzal.tripsplit.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.abzal.tripsplit.core.designsystem.components.TripTab

fun NavBackStackEntry.tripId(): String = checkNotNull(arguments?.getString(Routes.ARG_TRIP_ID))

fun NavBackStackEntry.expenseId(): String = checkNotNull(arguments?.getString(Routes.ARG_EXPENSE_ID))

/** Reads (and keeps) the currency picked on [Routes.CURRENCY_PICKER] for this destination. */
@Composable
fun NavBackStackEntry.pickedCurrency(): String? {
    val flow = remember(this) { savedStateHandle.getStateFlow<String?>(Routes.RESULT_CURRENCY, null) }
    val value by flow.collectAsStateWithLifecycle()
    return value
}

fun NavHostController.navigateAndClear(route: String, clearUpTo: String) {
    navigate(route) {
        popUpTo(clearUpTo) { inclusive = true }
        launchSingleTop = true
    }
}

/** Switches between the bottom tabs of a trip without piling up screens in the back stack. */
fun NavHostController.navigateToTripTab(tripId: String, tab: TripTab) {
    // Overview is the base of the tabs, so going there means popping back to it.
    if (tab == TripTab.Overview) {
        popBackStack(Routes.TRIP_OVERVIEW, inclusive = false)
        return
    }
    val route = when (tab) {
        TripTab.Expenses -> Routes.expenseHistory(tripId)
        TripTab.Balances -> Routes.balances(tripId)
        else -> Routes.insights(tripId)
    }
    navigate(route) {
        // Other tabs replace each other on top of Overview instead of stacking up.
        popUpTo(Routes.TRIP_OVERVIEW)
        launchSingleTop = true
    }
}
