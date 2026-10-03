package com.abzal.tripsplit.core.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.abzal.tripsplit.core.navigation.Routes
import com.abzal.tripsplit.core.navigation.navigateAndClear
import com.abzal.tripsplit.features.autharization.presentation.PasswordResetRoute
import com.abzal.tripsplit.features.autharization.presentation.ProfileRoute
import com.abzal.tripsplit.features.autharization.presentation.SignInRoute
import com.abzal.tripsplit.features.autharization.presentation.SignUpRoute

/** Authorization & account */
fun NavGraphBuilder.authGraph(navController: NavHostController) {
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
}
