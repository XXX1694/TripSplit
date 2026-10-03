package com.abzal.tripsplit.core.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.abzal.tripsplit.core.navigation.Routes
import com.abzal.tripsplit.core.navigation.navigateToTripTab
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.balances.presentation.BalancesRoute
import com.abzal.tripsplit.features.balances.presentation.OptimizedSettlementRoute
import com.abzal.tripsplit.features.balances.presentation.RecordSettlementRoute

/** Balances & settlements */
fun NavGraphBuilder.balancesGraph(navController: NavHostController) {
        composable(Routes.BALANCES) { entry ->
            val tripId = entry.tripId()
            BalancesRoute(
                onTabClick = { navController.navigateToTripTab(tripId, it) },
                onOptimizedClick = { navController.navigate(Routes.optimizedSettlement(tripId)) },
                onRecordSettlementClick = { navController.navigate(Routes.recordSettlement(tripId)) },
            )
        }
        composable(Routes.OPTIMIZED_SETTLEMENT) { entry ->
            val tripId = entry.tripId()
            OptimizedSettlementRoute(
                onBackClick = { navController.popBackStack() },
                onRecordSettlementClick = { navController.navigate(Routes.recordSettlement(tripId)) },
            )
        }
        composable(Routes.RECORD_SETTLEMENT) {
            RecordSettlementRoute(
                onBackClick = { navController.popBackStack() },
                onRecorded = { navController.popBackStack() },
            )
        }
}
