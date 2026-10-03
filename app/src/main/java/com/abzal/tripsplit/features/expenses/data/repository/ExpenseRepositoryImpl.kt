package com.abzal.tripsplit.features.expenses.data.repository

import com.abzal.tripsplit.core.navigation.expenseId
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.expenses.domain.model.Expense
import com.abzal.tripsplit.features.expenses.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class ExpenseRepositoryImpl : ExpenseRepository {
    private val expenses = MutableStateFlow<List<Expense>>(emptyList())

    override fun observeExpenses(tripId: String): Flow<List<Expense>> =
        expenses.map { list -> list.filter { it.tripId == tripId }.sortedByDescending { it.dateMillis } }

    override fun observeExpense(expenseId: String): Flow<Expense?> =
        expenses.map { list -> list.firstOrNull { it.id == expenseId } }

    override suspend fun addExpense(expense: Expense) {
        expenses.update { it + expense }
    }

    override suspend fun updateExpense(expense: Expense) {
        expenses.update { list -> list.map { if (it.id == expense.id) expense else it } }
    }

    override suspend fun deleteExpense(expenseId: String) {
        expenses.update { list -> list.filterNot { it.id == expenseId } }
    }
}
