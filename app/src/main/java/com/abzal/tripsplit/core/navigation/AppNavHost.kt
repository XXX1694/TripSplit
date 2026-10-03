package com.abzal.tripsplit.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.abzal.tripsplit.core.navigation.graphs.authGraph
import com.abzal.tripsplit.core.navigation.graphs.balancesGraph
import com.abzal.tripsplit.core.navigation.graphs.expensesGraph
import com.abzal.tripsplit.core.navigation.graphs.insightsGraph
import com.abzal.tripsplit.core.navigation.graphs.participantsGraph
import com.abzal.tripsplit.core.navigation.graphs.tripsGraph

/** Root navigation: one NavHost, every feature adds its screens in its own graph file. */
@Composable
fun AppNavHost(
    startDestination: String,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        authGraph(navController)
        tripsGraph(navController)
        expensesGraph(navController)
        participantsGraph(navController)
        balancesGraph(navController)
        insightsGraph(navController)
    }
}
