package com.abzal.tripsplit.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abzal.tripsplit.core.designsystem.components.TripTab
import com.abzal.tripsplit.features.autharization.presentation.PasswordResetRoute
import com.abzal.tripsplit.features.autharization.presentation.ProfileRoute
import com.abzal.tripsplit.features.autharization.presentation.SignInRoute
import com.abzal.tripsplit.features.autharization.presentation.SignUpRoute
import com.abzal.tripsplit.features.balances.presentation.BalancesRoute
import com.abzal.tripsplit.features.balances.presentation.OptimizedSettlementRoute
import com.abzal.tripsplit.features.balances.presentation.RecordSettlementRoute
import com.abzal.tripsplit.features.expenses.presentation.AddExpenseRoute
import com.abzal.tripsplit.features.expenses.presentation.CurrencyPickerRoute
import com.abzal.tripsplit.features.expenses.presentation.EditExpenseRoute
import com.abzal.tripsplit.features.expenses.presentation.ExpenseDetailRoute
import com.abzal.tripsplit.features.expenses.presentation.ExpenseHistoryRoute
import com.abzal.tripsplit.features.expenses.presentation.TripOverviewRoute
import com.abzal.tripsplit.features.home.presentation.HomeRoute
import com.abzal.tripsplit.features.insights.presentation.SpendingInsightsRoute
import com.abzal.tripsplit.features.participants.presentation.AddParticipantRoute
import com.abzal.tripsplit.features.participants.presentation.InvitationManagementRoute
import com.abzal.tripsplit.features.participants.presentation.InviteParticipantsRoute
import com.abzal.tripsplit.features.participants.presentation.JoinTripRoute
import com.abzal.tripsplit.features.participants.presentation.ParticipantManagementRoute
import com.abzal.tripsplit.features.trips.presentation.CreateTripRoute
import com.abzal.tripsplit.features.trips.presentation.DeleteTripRoute
import com.abzal.tripsplit.features.trips.presentation.EditTripRoute

private fun NavBackStackEntry.tripId(): String = checkNotNull(arguments?.getString(Routes.ARG_TRIP_ID))
private fun NavBackStackEntry.expenseId(): String = checkNotNull(arguments?.getString(Routes.ARG_EXPENSE_ID))

/** Reads (and keeps) the currency picked on [Routes.CURRENCY_PICKER] for this destination. */
@Composable
private fun NavBackStackEntry.pickedCurrency(): String? {
    val flow = remember(this) { savedStateHandle.getStateFlow<String?>(Routes.RESULT_CURRENCY, null) }
    val value by flow.collectAsStateWithLifecycle()
    return value
}

private fun NavHostController.navigateAndClear(route: String, clearUpTo: String) {
    navigate(route) {
        popUpTo(clearUpTo) { inclusive = true }
        launchSingleTop = true
    }
}

/** Switches between the bottom tabs of a trip without piling up screens in the back stack. */
private fun NavHostController.navigateToTripTab(tripId: String, tab: TripTab) {
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
        popUpTo(Routes.TRIP_OVERVIEW)
        launchSingleTop = true
    }
}

@Composable
fun AppNavHost(
    startDestination: String,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {

        // ---------- Authorization & account ----------
        composable(Routes.SIGN_IN) {
            SignInRoute(
                onSignedIn = { navController.navigateAndClear(Routes.HOME, Routes.SIGN_IN) },
                onSignUpClick = { navController.navigate(Routes.SIGN_UP) },
                onForgotPasswordClick = { navController.navigate(Routes.PASSWORD_RESET) },
            )
        }
        composable(Routes.SIGN_UP) {
            SignUpRoute(
                onSignedUp = { navController.navigateAndClear(Routes.HOME, Routes.SIGN_IN) },
                onBackClick = { navController.popBackStack() },
            )
        }
        composable(Routes.PASSWORD_RESET) {
            PasswordResetRoute(
                onBackClick = { navController.popBackStack() },
                onBackToSignInClick = { navController.popBackStack(Routes.SIGN_IN, inclusive = false) },
            )
        }
        composable(Routes.PROFILE) {
            ProfileRoute(
                onBackClick = { navController.popBackStack() },
                onSignedOut = { navController.navigateAndClear(Routes.SIGN_IN, Routes.HOME) },
            )
        }

        // ---------- Trips ----------
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

        // ---------- Expenses ----------
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

        // ---------- Participants & invitations ----------
        composable(Routes.PARTICIPANTS) { entry ->
            val tripId = entry.tripId()
            ParticipantManagementRoute(
                onBackClick = { navController.popBackStack() },
                onAddParticipantClick = { navController.navigate(Routes.addParticipant(tripId)) },
                onInviteClick = { navController.navigate(Routes.inviteParticipants(tripId)) },
                onInvitationsClick = { navController.navigate(Routes.invitations(tripId)) },
            )
        }
        composable(Routes.ADD_PARTICIPANT) {
            AddParticipantRoute(
                onBackClick = { navController.popBackStack() },
                onAdded = { navController.popBackStack() },
            )
        }
        composable(Routes.INVITE_PARTICIPANTS) {
            InviteParticipantsRoute(onBackClick = { navController.popBackStack() })
        }
        composable(Routes.INVITATIONS) { entry ->
            val tripId = entry.tripId()
            InvitationManagementRoute(
                onBackClick = { navController.popBackStack() },
                onInviteClick = { navController.navigate(Routes.inviteParticipants(tripId)) },
            )
        }

        // ---------- Balances & settlements ----------
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

        // ---------- Insights ----------
        composable(Routes.INSIGHTS) {
            SpendingInsightsRoute(onBackClick = { navController.popBackStack() })
        }
    }
}
