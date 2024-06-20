package com.obcteam.obct.data.remote.models

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("time_using_electronic_devices")
	val timeUsingElectronicDevices: Int? = null,

	@field:SerializedName("number_of_main_meals")
	val numberOfMainMeals: Int? = null,

	@field:SerializedName("frequency_of_physical_activity")
	val frequencyOfPhysicalActivity: Int? = null,

	@field:SerializedName("Prediction")
	val prediction: Prediction? = null,

	@field:SerializedName("smoke")
	val smoke: Int? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("consumption_of_food_between_meals")
	val consumptionOfFoodBetweenMeals: Int? = null,

	@field:SerializedName("frequently_consumed_high_calorie_food")
	val frequentlyConsumedHighCalorieFood: Int? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("consumption_of_water_daily")
	val consumptionOfWaterDaily: Int? = null,

	@field:SerializedName("type_of_transportation_used")
	val typeOfTransportationUsed: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("monitor_calorie_intake")
	val monitorCalorieIntake: Int? = null,

	@field:SerializedName("family_history_with_overweight")
	val familyHistoryWithOverweight: Int? = null,

	@field:SerializedName("frequency_of_consumption_of_vegetables")
	val frequencyOfConsumptionOfVegetables: Int? = null,

	@field:SerializedName("consumption_of_alcohol")
	val consumptionOfAlcohol: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("height")
	val height: Double? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class RecommendationItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("recommendation")
	val recommendation: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("predictionId")
	val predictionId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Prediction(

	@field:SerializedName("inputDataId")
	val inputDataId: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("prediction")
	val prediction: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("Recommendation")
	val recommendation: List<RecommendationItem> = emptyList(),

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
