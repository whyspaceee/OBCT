package com.obcteam.obct.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.obcteam.obct.domain.repository.AuthRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authRepository: AuthRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            authRepository.logout()
        }

        return response
    }

}