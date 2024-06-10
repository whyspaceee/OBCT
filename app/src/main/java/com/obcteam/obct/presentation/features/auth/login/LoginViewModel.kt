package com.obcteam.obct.presentation.features.auth.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.obcteam.obct.data.repository.FirebaseAuthRepository
import com.obcteam.obct.domain.mvi.MVI
import com.obcteam.obct.domain.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: FirebaseAuthRepository,
    val credentialManager: androidx.credentials.CredentialManager
) : ViewModel(), MVI<LoginState, LoginAction, LoginSideEffect> by mvi(LoginState()
) {

    override fun onAction(uiAction: LoginAction) {
        when (uiAction) {
            is LoginAction.LoginWithGoogleCredential -> loginWithGoogleCredential(uiAction.getCredentialResponse)
        }
    }

    private fun loginWithGoogleCredential(result: GetCredentialResponse) {
        viewModelScope.launch {
            when (result.credential) {
                is CustomCredential -> {
                    if (result.credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        updateUiState {
                            println("isLoading")
                            copy(isLoading = true)
                        }
                        try {
                            val credential = GoogleIdTokenCredential
                                .createFrom(result.credential.data)
                            val token = credential.idToken
                            authRepository.loginWithGoogle(token)
                        } catch (e: Exception) {
                            viewModelScope.emitSideEffect(LoginSideEffect.ShowError("Error"))
                            Log.e(TAG, "Error", e)
                            updateUiState {
                                copy(isLoading = false)
                            }
                        }
                    } else {
                        viewModelScope.emitSideEffect(LoginSideEffect.ShowError("Unexpected type of credential"))
                        Log.e(TAG, "Unexpected type of credential")
                    }
                }

                else -> {
                    viewModelScope.emitSideEffect(LoginSideEffect.ShowError("Unexpected type of credential"))
                    Log.e(TAG, "Unexpected type of credential")
                }
            }
        }

    }

}



