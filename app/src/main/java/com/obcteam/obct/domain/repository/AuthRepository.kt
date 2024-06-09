package com.obcteam.obct.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.obcteam.obct.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getUserFlow(): Flow<FirebaseUser?>

    fun getCurrentUser (): FirebaseUser?
    suspend fun loginWithGoogle(idToken: String)
    suspend fun register(email: String, password: String, name: String)
    suspend fun getProfile(): User?
    fun logout()
}