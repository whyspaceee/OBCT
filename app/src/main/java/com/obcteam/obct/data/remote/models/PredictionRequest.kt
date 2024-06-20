package com.obcteam.obct.data.remote.models

data class PredictionRequest(
    val height: Double,
val weight: Int,
    val family_history_with_overweight: Int,
    val frequently_consumed_high_calorie_food: Int,
    val frequency_of_consumption_of_vegetables: Int,
    val number_of_main_meals: Int,
    val consumption_of_food_between_meals: Int,
    val smoke: Int,
    val consumption_of_water_daily : Int,
    val monitor_calorie_intake : Int,
    val frequency_of_physical_activity: Int,
    val time_using_electronic_devices: Int,
    val consumption_of_alcohol: Int,
    val type_of_transportation_used: Int,
)