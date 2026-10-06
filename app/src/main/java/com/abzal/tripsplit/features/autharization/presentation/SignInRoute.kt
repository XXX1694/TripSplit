package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun SignInRoute(
    onSignedIn: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    viewModel: SignInViewModel = injectedViewModel { c, _ -> SignInViewModel(c.authRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SignInScreen(
        uiState = uiState,
        onSignInClick = { email, password -> viewModel.signIn(email, password, onSignedIn) },
        onSignUpClick = onSignUpClick,
        onForgotPasswordClick = onForgotPasswordClick,
        onDemoClick = { viewModel.continueAsDemo(onSignedIn) },
    )
}
