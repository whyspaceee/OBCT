package com.obcteam.obct.domain.repository

import com.obcteam.obct.data.remote.models.PredictionRequest
import com.obcteam.obct.data.remote.models.PredictionResponse

interface OBCTRepository {
    suspend fun lastPrediction() : PredictionResponse?

    suspend fun getPredictionHistory(take: Int, skip: Int) : List<PredictionResponse>
    suspend fun predict(predictionRequest: PredictionRequest)
}