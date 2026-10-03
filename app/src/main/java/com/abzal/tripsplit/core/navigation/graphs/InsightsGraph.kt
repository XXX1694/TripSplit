package com.abzal.tripsplit.core.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.abzal.tripsplit.core.navigation.Routes
import com.abzal.tripsplit.core.navigation.navigateToTripTab
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.insights.presentation.SpendingInsightsRoute

/** Insights */
fun NavGraphBuilder.insightsGraph(navController: NavHostController) {
        composable(Routes.INSIGHTS) { entry ->
            SpendingInsightsRoute(onTabClick = { navController.navigateToTripTab(entry.tripId(), it) })
        }
}
