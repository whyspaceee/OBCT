package com.obcteam.obct.domain.repository

import com.obcteam.obct.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val tokenFlow: Flow<String?>
    fun loginWithGoogle(idToken: String)
    suspend fun register(email: String, password: String, name: String)
    suspend fun getProfile(): User?
    fun logout()
}