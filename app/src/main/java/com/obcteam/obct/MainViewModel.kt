package com.obcteam.obct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class MainViewModel @Inject constructor(authRepository: AuthRepository) :
    ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val authState = authRepository.tokenFlow.mapLatest { token ->
        println(token)
        if (token == null) {
            AuthState.Unauthenticated
        } else {
            try {
                val profile = authRepository.getProfile()
                if (profile == null) {
                    AuthState.NotRegistered(token)
                } else {
                    AuthState.Authenticated(profile)
                }
            } catch (e: HttpException) {
                AuthState.NotRegistered(token)
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
    data class NotRegistered(val token: String) : AuthState()
}