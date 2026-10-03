package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun SignUpRoute(
    onSignedUp: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: SignUpViewModel = injectedViewModel { c, _ -> SignUpViewModel(c.authRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SignUpScreen(
        uiState = uiState,
        onSignUpClick = { name, email, password -> viewModel.signUp(name, email, password, onSignedUp) },
        onBackClick = onBackClick,
        onSignInClick = onBackClick,
    )
}
