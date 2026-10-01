package com.abzal.tripsplit.features.expenses.presentation

import androidx.lifecycle.ViewModel
import com.abzal.tripsplit.features.expenses.domain.model.CurrencyInfo
import com.abzal.tripsplit.features.expenses.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CurrencyPickerUiState(
    val currencies: List<CurrencyInfo> = emptyList(),
    val query: String = "",
    val selectedCode: String? = null,
) {
    /** Currencies that match the search by name or code. */
    val visibleCurrencies: List<CurrencyInfo>
        get() = currencies.filter {
            it.name.contains(query, ignoreCase = true) || it.code.contains(query, ignoreCase = true)
        }

    val selectedCurrency: CurrencyInfo?
        get() = currencies.firstOrNull { it.code == selectedCode }
}

class CurrencyPickerViewModel(
    currencyRepository: CurrencyRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurrencyPickerUiState(currencies = currencyRepository.getCurrencies()))
    val uiState: StateFlow<CurrencyPickerUiState> = _uiState.asStateFlow()

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun onCurrencySelect(code: String) {
        _uiState.update { it.copy(selectedCode = code) }
    }
}
