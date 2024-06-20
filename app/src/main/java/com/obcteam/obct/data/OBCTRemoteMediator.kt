package com.obcteam.obct.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.obcteam.obct.data.local.models.OBCTDatabase
import com.obcteam.obct.data.local.models.PredictionHistoryWithReccomendation
import com.obcteam.obct.domain.mapper.toPredictionHistoryEntity
import com.obcteam.obct.domain.mapper.toRecommendationEntity
import com.obcteam.obct.domain.repository.OBCTRepository
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class OBCTRemoteMediator(
private val obctRepository: OBCTRepository,
private val predictionDb: OBCTDatabase
) : RemoteMediator<Int, PredictionHistoryWithReccomendation>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PredictionHistoryWithReccomendation>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {

                    val lastItem = state.lastItemOrNull()

                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.predictionHistory.page) + 1
                    }
                }
            }


            val stories = obctRepository.getPredictionHistory(
                take = state.config.pageSize,
                skip = (loadKey - 1) * state.config.pageSize
            )

            predictionDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    predictionDb.dao.deleteAll()
                }

                val entities = stories.mapIndexed { index, predictionHistory ->
                    predictionHistory.toPredictionHistoryEntity(
                        indexInPage = index,
                        page = loadKey
                    )
                }

                val recommendation = stories.flatMap {
                    it.toRecommendationEntity()
                }

                predictionDb.dao.upsertPredictions(entities)
                predictionDb.dao.upsertRecommendations(recommendation)
            }


            MediatorResult.Success(endOfPaginationReached = stories.isEmpty())

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }    }

}