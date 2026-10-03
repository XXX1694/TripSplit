package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel

@Composable
fun ProfileRoute(
    onBackClick: () -> Unit,
    onSignedOut: () -> Unit,
    viewModel: ProfileViewModel = injectedViewModel { c, _ -> ProfileViewModel(c.authRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProfileScreen(
        uiState = uiState,
        onSignOutClick = { viewModel.signOut(onSignedOut) },
        onClearLocalDataClick = { viewModel.clearLocalData(onSignedOut) },
        onBackClick = onBackClick,
    )
}
