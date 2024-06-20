package com.obcteam.obct.data.local.models

import androidx.room.Database
import androidx.room.Relation
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.obcteam.obct.data.local.PredictionDao

@Database(
    entities = [PredictionHistoryEntity::class, RecommendationEntity::class],
    version = 1,
)
abstract class OBCTDatabase : RoomDatabase() {

    abstract val dao: PredictionDao
}