package com.obcteam.obct.presentation.features.auth.login

import androidx.credentials.GetCredentialResponse

data class LoginState(
    val isLoading: Boolean = false
)

sealed interface LoginAction {
    data class LoginWithGoogleCredential(val getCredentialResponse: GetCredentialResponse) : LoginAction
}

sealed interface LoginSideEffect {
    data class ShowError(val message: String) : LoginSideEffect
}

