package com.obcteam.obct.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.obcteam.obct.data.remote.models.RegisterRequest
import com.obcteam.obct.data.remote.OBCTService
import com.obcteam.obct.domain.models.User
import com.obcteam.obct.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val obctService: OBCTService
) : AuthRepository {


    override fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun register(dateOfBirth: String, gender: String) {
        obctService.register(
            RegisterRequest(
                dateOfBirth = dateOfBirth,
                gender = gender
            )
        )
    }

    override fun refreshToken() {
        val currentUser = firebaseAuth.currentUser ?: return
        firebaseAuth.updateCurrentUser(currentUser)
    }

    override suspend fun getUser(): User? {
        return obctService.getProfile()
    }

    override fun getUserFlow(): Flow<FirebaseUser?> {
        return callbackFlow {
            trySend(firebaseAuth.currentUser)
            val listener = FirebaseAuth.AuthStateListener { auth ->
                trySend(auth.currentUser)
            }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose {
                firebaseAuth.removeAuthStateListener(listener)
            }
        }
    }

    override fun getCurrentFirebaseUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override suspend fun loginWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
    }

}