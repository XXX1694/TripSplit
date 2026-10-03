package com.abzal.tripsplit.features.autharization.data.repository

import com.abzal.tripsplit.features.autharization.domain.model.User
import com.abzal.tripsplit.features.autharization.domain.repository.AuthRepository
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Local in-memory stub. Replace with Room/remote data source later. */
class AuthRepositoryImpl : AuthRepository {
    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    override suspend fun signIn(email: String, password: String): Result<User> {
        val user = User(id = UUID.randomUUID().toString(), name = email.substringBefore('@'), email = email)
        _currentUser.value = user
        return Result.success(user)
    }

    override suspend fun signUp(name: String, email: String, password: String): Result<User> {
        val user = User(id = UUID.randomUUID().toString(), name = name, email = email)
        _currentUser.value = user
        return Result.success(user)
    }

    override suspend fun requestPasswordReset(email: String): Result<Unit> = Result.success(Unit)

    override suspend fun signOut() {
        _currentUser.value = null
    }

    override suspend fun clearLocalData() {
        _currentUser.value = null
    }
}
