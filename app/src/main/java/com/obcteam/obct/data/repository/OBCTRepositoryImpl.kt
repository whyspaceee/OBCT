package com.obcteam.obct.data.repository

import com.obcteam.obct.data.remote.models.PredictionRequest
import com.obcteam.obct.data.remote.models.PredictionResponse
import com.obcteam.obct.data.remote.OBCTService
import com.obcteam.obct.domain.repository.OBCTRepository

class OBCTRepositoryImpl(
    private val obctService: OBCTService
) : OBCTRepository {
    override suspend fun lastPrediction(): PredictionResponse? {
        return obctService.getPredictionHistory(
            take = 1,
        ).firstOrNull()
    }

    override suspend fun getPredictionHistory(take: Int, skip: Int): List<PredictionResponse> {
        return obctService.getPredictionHistory(
            take = take,
            skip = skip
        )
    }

    override suspend fun predict(
        predictionRequest: PredictionRequest
    ) {
        obctService.getPrediction(predictionRequest)
    }


}
