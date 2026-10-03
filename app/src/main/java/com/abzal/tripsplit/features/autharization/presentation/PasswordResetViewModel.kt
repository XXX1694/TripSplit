package com.abzal.tripsplit.features.autharization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abzal.tripsplit.features.autharization.domain.model.*
import com.abzal.tripsplit.features.autharization.domain.repository.AuthRepository
import com.abzal.tripsplit.features.balances.domain.model.*
import com.abzal.tripsplit.features.expenses.domain.model.*
import com.abzal.tripsplit.features.insights.domain.model.*
import com.abzal.tripsplit.features.participants.domain.model.*
import com.abzal.tripsplit.features.trips.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PasswordResetUiState(
    val isLoading: Boolean = false,
    val isSent: Boolean = false,
    val error: String? = null,
)

class PasswordResetViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PasswordResetUiState())
    val uiState: StateFlow<PasswordResetUiState> = _uiState.asStateFlow()

    fun requestReset(email: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            authRepository.requestPasswordReset(email)
                .onSuccess { _uiState.update { it.copy(isLoading = false, isSent = true) } }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }
}
