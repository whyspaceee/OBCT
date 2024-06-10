package com.obcteam.obct.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.obcteam.obct.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getUserFlow(): Flow<FirebaseUser?>

    fun getCurrentFirebaseUser(): FirebaseUser?
    suspend fun loginWithGoogle(idToken: String)
    fun logout()
    suspend fun register(
        dateOfBirth: String,
        gender: String,
    )

    suspend fun getUser(): User?

}