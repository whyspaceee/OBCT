package com.obcteam.obct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.obcteam.obct.data.remote.AuthInterceptor
import com.obcteam.obct.domain.models.User
import com.obcteam.obct.domain.repository.AuthRepository
import com.obcteam.obct.presentation.navigation.Graph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(authRepository: AuthRepository, authInterceptor: AuthInterceptor) :
    ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val authState = authRepository.getUserFlow().mapLatest { user ->
        if (user == null) {
            AuthState.Unauthenticated
        } else {
            user.getIdToken(true).addOnCompleteListener {
                if (it.isSuccessful) {
                    val idToken = it.result.token
                    authInterceptor.setToken(idToken)
                    println("idToken : $idToken")
                }
            }
            try {
                val profile = authRepository.getProfile()
                println("profile : $profile")
                if (profile == null) {
                    AuthState.NotRegistered(user)
                } else {
                    AuthState.Authenticated(profile)
                }
            } catch (e: HttpException) {
                AuthState.NotRegistered(user)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, AuthState.Loading)

    val startDestination = authState.mapLatest {
        when (it) {
            is AuthState.Unauthenticated -> Graph.AUTH
            is AuthState.Authenticated -> Graph.MAIN
            is AuthState.NotRegistered -> Graph.ONBOARDING
            is AuthState.Loading -> null
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

}


sealed class AuthState {
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data class Authenticated(val user: User) : AuthState()
    data class NotRegistered(val firebaseUser: FirebaseUser) : AuthState()
}