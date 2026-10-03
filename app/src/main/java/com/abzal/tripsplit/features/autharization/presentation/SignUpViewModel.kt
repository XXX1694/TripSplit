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

data class SignUpUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
)

class SignUpViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun signUp(name: String, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            authRepository.signUp(name, email, password)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                    onSuccess()
                }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }
}
