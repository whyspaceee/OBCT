package com.obcteam.obct.domain.mapper

import com.obcteam.obct.data.local.models.PredictionEntity
import com.obcteam.obct.data.local.models.PredictionHistoryEntity
import com.obcteam.obct.data.local.models.RecommendationEntity
import com.obcteam.obct.data.remote.models.PredictionResponse

fun PredictionResponse.toPredictionHistoryEntity(indexInPage: Int, page: Int) =
    PredictionHistoryEntity(
        id = id ?: "",
        userId = userId ?: "",
        height = height ?: 0.0,
        weight = weight ?: 0,
        familyHistoryWithOverweight = familyHistoryWithOverweight ?: 0,
        frequentlyConsumedHighCalorieFood = frequentlyConsumedHighCalorieFood ?: 0,
        consumptionOfAlcohol = consumptionOfAlcohol ?: 0,
        consumptionOfFoodBetweenMeals = consumptionOfFoodBetweenMeals ?: 0,
        consumptionOfWaterDaily = consumptionOfWaterDaily ?: 0,
        smoke = smoke ?: 0,
        createdAt = createdAt ?: "",
        frequencyOfConsumptionOfVegetables = frequencyOfConsumptionOfVegetables ?: 0,
        frequencyOfPhysicalActivity = frequencyOfPhysicalActivity ?: 0,
        monitorCalorieIntake = monitorCalorieIntake ?: 0,
        numberOfMainMeals = numberOfMainMeals ?: 0,
        timeUsingElectronicDevices = timeUsingElectronicDevices ?: 0,
        indexInPage = indexInPage,
        page = page,
        typeOfTransportationUsed = typeOfTransportationUsed ?: 0,
        updatedAt = updatedAt ?: "",

        prediction = PredictionEntity(
            id = prediction?.id ?: "",
            userId = prediction?.userId ?: "",
            inputDataId = prediction?.inputDataId ?: "",
            prediction = prediction?.prediction ?: 0,
            createdAt = prediction?.createdAt ?: "",
            updatedAt = prediction?.updatedAt ?: "",
        )
    )

fun PredictionResponse.toRecommendationEntity() =
    prediction?.recommendation?.map {
        RecommendationEntity(
            id = it.id ?: "",
            predictionId = it.predictionId ?: "",
            userId = it.userId ?: "",
            recommendation = it.recommendation ?: "",
            createdAt = it.createdAt ?: "",
            updatedAt = it.updatedAt ?: ""
        )
    } ?: emptyList()
