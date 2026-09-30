package com.abzal.tripsplit.features.autharization.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abzal.tripsplit.core.di.injectedViewModel
import com.abzal.tripsplit.features.autharization.domain.model.*
import com.abzal.tripsplit.features.balances.domain.model.*
import com.abzal.tripsplit.features.expenses.domain.model.*
import com.abzal.tripsplit.features.insights.domain.model.*
import com.abzal.tripsplit.features.participants.domain.model.*
import com.abzal.tripsplit.features.trips.domain.model.*

@Composable
fun PasswordResetRoute(
    onBackClick: () -> Unit,
    onBackToSignInClick: () -> Unit,
    viewModel: PasswordResetViewModel = injectedViewModel { c, h -> PasswordResetViewModel(c.authRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PasswordResetScreen(
        uiState = uiState,
        onSendClick = viewModel::requestReset,
        onBackClick = onBackClick,
        onBackToSignInClick = onBackToSignInClick,
    )
}

@Composable
fun PasswordResetScreen(
    uiState: PasswordResetUiState,
    onSendClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBackToSignInClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "PasswordReset")
    }
}
