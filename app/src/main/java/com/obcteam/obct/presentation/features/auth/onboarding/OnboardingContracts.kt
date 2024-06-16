package com.obcteam.obct.presentation.features.auth.onboarding

data class OnboardingState(
    val height: Int? = null,
    val weight: Int? = null,
    val familyHistory: Boolean? = null,
    val eatingHabits: Boolean? = null,
    val vegetables: Vegetables? = null,
    val smoking: Boolean? = null,
    val monitorCalorieIntake: Boolean? = null,
    val mainMeal: MainMeal? = null,
    val foodBetweenMeals: FoodBetweenMeals? = null
)

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

interface OnboardingAction {
    data class SetHeight(val height: Int) : OnboardingAction
    data class SetWeight(val weight: Int) : OnboardingAction
    object Submit : OnboardingAction
}

interface OnboardingSideEffect



