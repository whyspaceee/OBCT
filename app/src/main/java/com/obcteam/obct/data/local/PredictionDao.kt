package com.obcteam.obct.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.obcteam.obct.data.local.models.PredictionEntity
import com.obcteam.obct.data.local.models.PredictionHistoryEntity
import com.obcteam.obct.data.local.models.PredictionHistoryWithReccomendation
import com.obcteam.obct.data.local.models.RecommendationEntity

@Dao
interface PredictionDao {
    @Upsert
    suspend fun upsertPredictions(predictions: List<PredictionHistoryEntity>)

    @Upsert
    suspend fun upsertRecommendations(recommendations: List<RecommendationEntity>)
    @Query("SELECT * FROM PredictionHistoryEntity")
    fun pagingSource(): PagingSource<Int, PredictionHistoryWithReccomendation>

    @Query("DELETE FROM PredictionHistoryEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM PredictionHistoryEntity WHERE id = :id")
    suspend fun getPredictionById(id: String): PredictionHistoryWithReccomendation

}