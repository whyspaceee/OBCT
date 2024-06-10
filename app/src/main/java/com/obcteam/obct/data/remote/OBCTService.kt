package com.obcteam.obct.data.remote

import com.obcteam.obct.data.models.RegisterRequest
import com.obcteam.obct.domain.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OBCTService {
    @POST("auth/register")
    suspend fun register(
        @Body body : RegisterRequest
    )

    @GET("user/profile")
    suspend fun getProfile() : User
}