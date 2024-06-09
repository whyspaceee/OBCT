package com.obcteam.obct.data.remote

import com.obcteam.obct.data.models.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface OBCTService {
    @POST("auth/register")
    suspend fun register(
        @Body body : RegisterRequest
    )


}