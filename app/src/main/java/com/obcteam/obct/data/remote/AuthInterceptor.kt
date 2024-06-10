package com.obcteam.obct.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.obcteam.obct.domain.repository.AuthRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
) : Interceptor {
    private var token: String? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        val response = chain.proceed(request)

        return response
    }

    fun setToken (token: String?) {
        this.token = token
    }

}