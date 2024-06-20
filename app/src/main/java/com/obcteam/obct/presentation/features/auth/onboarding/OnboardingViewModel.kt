package com.obcteam.obct.presentation.features.auth.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obcteam.obct.data.remote.models.PredictionRequest
import com.obcteam.obct.domain.mvi.MVI
import com.obcteam.obct.domain.mvi.mvi
import com.obcteam.obct.domain.repository.OBCTRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    val obctRepository: OBCTRepository
) : ViewModel(),
    MVI<OnboardingState, OnboardingAction, OnboardingSideEffect> by mvi(
        OnboardingState(weight = 65)
    ) {

    override fun onAction(uiAction: OnboardingAction) {
        when (uiAction) {
            is OnboardingAction.SetHeight -> {
                updateUiState { copy(height = uiAction.height) }
            }

            is OnboardingAction.SetWeight -> {
                updateUiState { copy(weight = uiAction.weight) }
            }

            is OnboardingAction.SetFamilyHistory -> {
                updateUiState { copy(familyHistory = uiAction.familyHistory) }
            }

            is OnboardingAction.SetFoodBetweenMeals -> {
                updateUiState { copy(foodBetweenMeals = uiAction.foodBetweenMeals) }
            }

            is OnboardingAction.SetMainMeal -> {
                updateUiState { copy(mainMeal = uiAction.mainMeal) }
            }

            is OnboardingAction.SetMonitorCalorieIntake -> {
                updateUiState { copy(monitorCalorieIntake = uiAction.monitorCalorieIntake) }
            }

            is OnboardingAction.SetSmoking -> {
                updateUiState { copy(smoking = uiAction.smoking) }
            }

            is OnboardingAction.SetVegetables -> {
                updateUiState { copy(vegetables = uiAction.vegetables) }
            }

            is OnboardingAction.SetHighCaloricFoods -> {
                updateUiState { copy(highCaloricFoods = uiAction.highCaloricFoods) }
            }

            is OnboardingAction.SetPhysicalActivity -> {
                updateUiState { copy(physicalActivity = uiAction.physicalActivity) }
            }

            is OnboardingAction.SetDailyWaterIntake -> {
                updateUiState {
                    copy(dailyWaterIntake = uiAction.dailyWaterIntake)
                }
            }

            is OnboardingAction.SetAlcoholConsumption -> {
                updateUiState {
                    copy(alcoholConsumption = uiAction.alcoholConsumption)
                }
            }

            is OnboardingAction.SetTimeOnElectronicDevices -> {
                updateUiState {
                    copy(timeOnElectronicDevices = uiAction.timeOnElectronicDevices)
                }
            }

            is OnboardingAction.SetTransportation -> {
                updateUiState {
                    copy(transportation = uiAction.transportation)
                }
            }

            is OnboardingAction.Submit -> {
                updateUiState {
                    copy(isSubmitting = true)
                }
                viewModelScope.launch {
                    try {
                        val value = uiState.value
                        obctRepository.predict(
                            PredictionRequest(
                                height = (value.height!!.toDouble() / 100),
                                weight = value.weight ?: 60,
                                family_history_with_overweight = if (value.familyHistory!!) 1 else 0,
                                frequently_consumed_high_calorie_food = if (value.highCaloricFoods!!) 1 else 0,
                                frequency_of_consumption_of_vegetables = value.vegetables!!.value,
                                number_of_main_meals = value.mainMeal!!.value,
                                consumption_of_food_between_meals = value.foodBetweenMeals!!.value,
                                smoke = if (value.smoking!!) 1 else 0,
                                consumption_of_water_daily = value.dailyWaterIntake!!.value,
                                monitor_calorie_intake = if (value.monitorCalorieIntake!!) 1 else 0,
                                frequency_of_physical_activity = value.physicalActivity!!.value,
                                time_using_electronic_devices = value.timeOnElectronicDevices!!.value,
                                consumption_of_alcohol = value.alcoholConsumption!!.value,
                                type_of_transportation_used = value.transportation!!.value
                            )
                        )
                        emitSideEffect(OnboardingSideEffect.NavigateToMain)

                    } catch (e: Exception) {
                        updateUiState {
                            copy(isSubmitting = false)
                        }
                    }
                }
            }
        }



        fun validateEatingHabits() {

        }
    }

}