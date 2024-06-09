package com.obcteam.obct.domain.repository

interface OBCTRepository {
    suspend fun register (
        dateOfBirth: String,
        gender : String,
    )
}