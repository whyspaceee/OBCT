package com.obcteam.obct.data.repository

import com.obcteam.obct.data.models.RegisterRequest
import com.obcteam.obct.data.remote.OBCTService
import com.obcteam.obct.domain.repository.OBCTRepository

class OBCTRepositoryImpl(
    private val obctService: OBCTService
) : OBCTRepository {
    override suspend fun register(dateOfBirth: String, gender: String) {
        obctService.register(
            RegisterRequest(
                dateOfBirth = dateOfBirth,
                gender = gender
            )
        )
    }
}