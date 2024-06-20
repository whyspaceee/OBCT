package com.obcteam.obct.data.local.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "PredictionHistoryEntity")
data class PredictionHistoryEntity (
    @PrimaryKey
    val id: String,
    val userId: String,
    val height: Double,
    val weight: Int,
    val familyHistoryWithOverweight: Int,
    val frequentlyConsumedHighCalorieFood: Int,
    val frequencyOfConsumptionOfVegetables: Int,
    val numberOfMainMeals: Int,
    val consumptionOfFoodBetweenMeals: Int,
    val smoke: Int,
    val consumptionOfWaterDaily: Int,
    val monitorCalorieIntake: Int,
    val frequencyOfPhysicalActivity: Int,
    val timeUsingElectronicDevices: Int,
    val consumptionOfAlcohol: Int,
    val typeOfTransportationUsed: Int,
    val createdAt: String,
    val updatedAt: String,
    val page : Int,
    val indexInPage : Int,

    @Embedded(prefix = "prediction_")
    val prediction: PredictionEntity
)
data class PredictionEntity (
    val id: String,
    val userId: String,
    val inputDataId: String,
    val prediction: Int,
    val createdAt: String,
    val updatedAt: String,
)

data class PredictionHistoryWithReccomendation(
    @Embedded
    val predictionHistory: PredictionHistoryEntity,
    @Relation(
        parentColumn = "prediction_id",
        entityColumn = "predictionId"
    )
    val recommendation: List<RecommendationEntity>
)



@Entity(tableName = "RecommendationEntity")
data class RecommendationEntity (
    @PrimaryKey
    val id: String,
    val userId: String,
    val predictionId: String,
    val recommendation: String,
    val createdAt: String,
    val updatedAt: String
)
