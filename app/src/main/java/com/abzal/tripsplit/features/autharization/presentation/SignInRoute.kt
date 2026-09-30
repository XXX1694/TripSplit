package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.runtime.Composable
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun SignInRoute(
    onSignedIn: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    viewModel: SignInViewModel = injectedViewModel { c, _ -> SignInViewModel(c.authRepository) },
) {
    SignInScreen(
        onSignInClick = { email, password -> viewModel.signIn(email, password, onSignedIn) },
        onSignUpClick = onSignUpClick,
        onForgotPasswordClick = onForgotPasswordClick,
    )
}
