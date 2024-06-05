package com.obcteam.obct.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.obcteam.obct.domain.models.User
import com.obcteam.obct.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override val tokenFlow: Flow<String?> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            if(auth.currentUser == null) {
                trySend(null)
            }
            auth.currentUser?.getIdToken(true)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result?.token != null) {
                        trySend(it.result.token)
                    } else {
                        trySend(null)
                    }
                } else {
                    trySend(null)
                }
            }

        }
        firebaseAuth.addAuthStateListener(authStateListener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun loginWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
    }

    override suspend fun register(email: String, password: String, name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getProfile(): User? {
        return null
    }
}