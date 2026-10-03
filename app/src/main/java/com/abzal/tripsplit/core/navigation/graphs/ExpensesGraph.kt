package com.abzal.tripsplit.core.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.abzal.tripsplit.core.navigation.Routes
import com.abzal.tripsplit.core.navigation.expenseId
import com.abzal.tripsplit.core.navigation.navigateToTripTab
import com.abzal.tripsplit.core.navigation.pickedCurrency
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.expenses.presentation.AddExpenseRoute
import com.abzal.tripsplit.features.expenses.presentation.CurrencyPickerRoute
import com.abzal.tripsplit.features.expenses.presentation.EditExpenseRoute
import com.abzal.tripsplit.features.expenses.presentation.ExpenseDetailRoute
import com.abzal.tripsplit.features.expenses.presentation.ExpenseHistoryRoute
import com.abzal.tripsplit.features.expenses.presentation.TripOverviewRoute

/** Expenses */
fun NavGraphBuilder.expensesGraph(navController: NavHostController) {
        composable(Routes.TRIP_OVERVIEW) { entry ->
            val tripId = entry.tripId()
            TripOverviewRoute(
                onBackClick = { navController.popBackStack() },
                onEditTripClick = { navController.navigate(Routes.editTrip(tripId)) },
                onAddExpenseClick = { navController.navigate(Routes.addExpense(tripId)) },
                onExpenseClick = { navController.navigate(Routes.expenseDetail(tripId, it)) },
                onExpenseHistoryClick = { navController.navigate(Routes.expenseHistory(tripId)) },
                onBalancesClick = { navController.navigate(Routes.balances(tripId)) },
                onParticipantsClick = { navController.navigate(Routes.participants(tripId)) },
                onInsightsClick = { navController.navigate(Routes.insights(tripId)) },
            )
        }
        composable(Routes.EXPENSE_HISTORY) { entry ->
            val tripId = entry.tripId()
            ExpenseHistoryRoute(
                onTabClick = { navController.navigateToTripTab(tripId, it) },
                onExpenseClick = { navController.navigate(Routes.expenseDetail(tripId, it)) },
                onAddExpenseClick = { navController.navigate(Routes.addExpense(tripId)) },
            )
        }
        composable(Routes.ADD_EXPENSE) { entry ->
            AddExpenseRoute(
                onBackClick = { navController.popBackStack() },
                onSaved = { navController.popBackStack() },
                onPickCurrencyClick = { navController.navigate(Routes.CURRENCY_PICKER) },
                pickedCurrency = entry.pickedCurrency(),
            )
        }
        composable(Routes.EXPENSE_DETAIL) { entry ->
            val tripId = entry.tripId()
            val expenseId = entry.expenseId()
            ExpenseDetailRoute(
                onBackClick = { navController.popBackStack() },
                onEditClick = { navController.navigate(Routes.editExpense(tripId, expenseId)) },
                onDeleted = { navController.popBackStack() },
            )
        }
        composable(Routes.EDIT_EXPENSE) { entry ->
            EditExpenseRoute(
                onBackClick = { navController.popBackStack() },
                onSaved = { navController.popBackStack() },
                onDeleted = { navController.popBackStack(Routes.EXPENSE_DETAIL, inclusive = true) },
                onPickCurrencyClick = { navController.navigate(Routes.CURRENCY_PICKER) },
                pickedCurrency = entry.pickedCurrency(),
            )
        }
        composable(Routes.CURRENCY_PICKER) {
            CurrencyPickerRoute(
                onBackClick = { navController.popBackStack() },
                onCurrencySelected = { code ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(Routes.RESULT_CURRENCY, code)
                    navController.popBackStack()
                },
            )
        }
}
