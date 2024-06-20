package com.obcteam.obct.presentation.features.auth.onboarding

data class OnboardingState(
    val height: Int? = null,
    val weight: Int? = null,
    val familyHistory: Boolean? = null,
    val vegetables: Vegetables? = null,
    val smoking: Boolean? = null,
    val monitorCalorieIntake: Boolean? = null,
    val mainMeal: MainMeal? = null,
    val foodBetweenMeals: FoodBetweenMeals? = null,
    val highCaloricFoods : Boolean? = null,
    val physicalActivity: PhysicalActivity? = null,
    val dailyWaterIntake: DailyWaterIntake? = null,
    val alcoholConsumption: AlcoholConsumption? = null,
    val transportation: Transportation? = null,
    val timeOnElectronicDevices: TimeOnElectronicDevices? = null,
    val isSubmitting : Boolean = false
)

sealed class TimeOnElectronicDevices(val value: Int) {
    object LessThan2Hour : TimeOnElectronicDevices(0)
    object Between3And5Hours : TimeOnElectronicDevices(1)
    object MoreThan5Hours : TimeOnElectronicDevices(2)
}

sealed class Transportation(val value: Int) {
    object Bike : Transportation(0)
    object Motorbike : Transportation(1)
    object Walk : Transportation(2)
    object Automobile : Transportation(3)
    object PublicTransport : Transportation(4)
}

sealed class FoodBetweenMeals(val value: Int) {
    object No : FoodBetweenMeals(0)
    object Sometimes : FoodBetweenMeals(1)
    object Often : FoodBetweenMeals(2)
    object Always : FoodBetweenMeals(3)
}

sealed class MainMeal(val value: Int) {
    object Three : MainMeal(2)
    object OneOrTwo : MainMeal(1)
    object MoreThanThree : MainMeal(3)
}

sealed class Vegetables(val value: Int) {
    object Yes : Vegetables(3)
    object No : Vegetables(1)
    object Maybe : Vegetables(2)
}

sealed class PhysicalActivity(val value: Int) {
    object None : PhysicalActivity(0)
    object OneTwo : PhysicalActivity(1)
    object ThreeFour : PhysicalActivity(2)
    object FiveOrMore : PhysicalActivity(3)
}

sealed class DailyWaterIntake(val value: Int) {
    object LessThan1L : DailyWaterIntake(1)
    object Between1And2L : DailyWaterIntake(2)
    object MoreThan2L : DailyWaterIntake(3)

}

sealed class AlcoholConsumption(val value: Int) {
    object Never : AlcoholConsumption(0)
    object Sometimes : AlcoholConsumption(1)
    object Freq : AlcoholConsumption(2)
    object Always : AlcoholConsumption(3)
}

sealed interface OnboardingAction {
    data class SetHeight(val height: Int) : OnboardingAction
    data class SetWeight(val weight: Int) : OnboardingAction

    data class SetFamilyHistory(val familyHistory: Boolean) : OnboardingAction

    data class SetPhysicalActivity(val physicalActivity: PhysicalActivity) : OnboardingAction

    data class SetVegetables(val vegetables: Vegetables) : OnboardingAction

    data class SetSmoking(val smoking: Boolean) : OnboardingAction

    data class SetMonitorCalorieIntake(val monitorCalorieIntake: Boolean) : OnboardingAction

    data class SetMainMeal(val mainMeal: MainMeal) : OnboardingAction

    data class SetFoodBetweenMeals(val foodBetweenMeals: FoodBetweenMeals) : OnboardingAction

    data class SetHighCaloricFoods(val highCaloricFoods: Boolean) : OnboardingAction

    data class SetDailyWaterIntake(val dailyWaterIntake: DailyWaterIntake) : OnboardingAction

    data class SetAlcoholConsumption(val alcoholConsumption: AlcoholConsumption) : OnboardingAction

    data class SetTransportation(val transportation: Transportation) : OnboardingAction

    data class SetTimeOnElectronicDevices(val timeOnElectronicDevices: TimeOnElectronicDevices) : OnboardingAction



    object Submit : OnboardingAction
}

sealed interface OnboardingSideEffect {
    data class Navigate(val route: String) : OnboardingSideEffect
    object NavigateToMain : OnboardingSideEffect
}



