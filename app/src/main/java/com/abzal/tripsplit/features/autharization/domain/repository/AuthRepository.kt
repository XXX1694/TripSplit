package com.abzal.tripsplit.features.autharization.domain.repository

import com.abzal.tripsplit.features.autharization.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val currentUser: StateFlow<User?>

    suspend fun signIn(email: String, password: String): Result<User>
    /** Signs in with a ready-made demo user, no registration needed. */
    suspend fun signInAsDemo(): User
    suspend fun signUp(name: String, email: String, password: String): Result<User>
    suspend fun requestPasswordReset(email: String): Result<Unit>
    suspend fun signOut()
    suspend fun clearLocalData()
}
