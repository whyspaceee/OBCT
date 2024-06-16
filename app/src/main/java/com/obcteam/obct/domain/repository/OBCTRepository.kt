package com.obcteam.obct.domain.repository

import com.obcteam.obct.domain.models.User

interface OBCTRepository {
    suspend fun lastPrediction() : String?

}