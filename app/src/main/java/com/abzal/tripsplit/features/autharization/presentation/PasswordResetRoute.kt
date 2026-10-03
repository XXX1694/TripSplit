package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun PasswordResetRoute(
    onBackClick: () -> Unit,
    onBackToSignInClick: () -> Unit,
    viewModel: PasswordResetViewModel = injectedViewModel { c, _ -> PasswordResetViewModel(c.authRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PasswordResetScreen(
        uiState = uiState,
        onSendClick = viewModel::requestReset,
        onBackClick = onBackClick,
        onBackToSignInClick = onBackToSignInClick,
    )
}
