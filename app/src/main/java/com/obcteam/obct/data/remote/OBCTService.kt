package com.obcteam.obct.data.remote

import com.obcteam.obct.data.remote.models.PredictionRequest
import com.obcteam.obct.data.remote.models.PredictionResponse
import com.obcteam.obct.data.remote.models.RegisterRequest
import com.obcteam.obct.domain.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OBCTService {
    @POST("auth/register")
    suspend fun register(
        @Body body: RegisterRequest
    )

    @GET("user/profile")
    suspend fun getProfile(): User

    @POST("prediction")
    suspend fun getPrediction(
        @Body body: PredictionRequest
    )

    @GET("prediction/history")
    suspend fun getPredictionHistory(
        @Query("take") take: Int? = 20,
        @Query("skip") skip: Int? = null
    ): List<PredictionResponse>
}