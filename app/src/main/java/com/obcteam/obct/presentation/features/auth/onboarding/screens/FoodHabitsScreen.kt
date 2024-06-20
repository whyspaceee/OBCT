package com.obcteam.obct.presentation.features.auth.onboarding.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.obcteam.obct.R
import com.obcteam.obct.presentation.features.auth.onboarding.FoodBetweenMeals
import com.obcteam.obct.presentation.features.auth.onboarding.MainMeal
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingAction
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingScreen
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingState
import com.obcteam.obct.presentation.features.auth.onboarding.Vegetables
import com.obcteam.obct.presentation.features.auth.onboarding.components.AnswerState
import com.obcteam.obct.presentation.features.auth.onboarding.components.ToggleableAnswer
import kotlin.reflect.KFunction1

@Composable
fun FoodHabitsScreen(
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit,
    navHostController: NavHostController
){
    Scaffold(
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(horizontal = 32.dp),
                containerColor = Color.Transparent
            ) {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    navHostController.navigate(OnboardingScreen.DailyHabits.route)
                }) {
                    Text(text = stringResource(R.string.cont))
                }
            }
        },
        topBar = {
            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 64.dp)
                    .fillMaxWidth(),
                text = "Eating Habits",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                fontSize = 24.sp
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Do you have a family history of obesity?",
            )
            Spacer(modifier = Modifier.height(8.dp))
            ToggleableAnswer(
                modifier = Modifier.fillMaxWidth(), state = arrayOf(
                    (AnswerState(
                        isEnabled = state.familyHistory == true,
                        label = "Yes",
                        onAction = {
                            onAction(OnboardingAction.SetFamilyHistory(true))
                        })),
                    AnswerState(
                        isEnabled = state.familyHistory == false,
                        label = "No",
                        onAction = {
                            onAction(OnboardingAction.SetFamilyHistory(false))
                        })
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Do you eat high caloric foods frequently?",
            )
            Spacer(modifier = Modifier.height(8.dp))
            ToggleableAnswer(
                modifier = Modifier.fillMaxWidth(), state = arrayOf(
                    (AnswerState(isEnabled = state.highCaloricFoods == true,
                        label = "Yes",
                        onAction = {
                            onAction(OnboardingAction.SetHighCaloricFoods(true))
                        })), AnswerState(isEnabled = state.highCaloricFoods == false,
                        label = "No",
                        onAction = {
                            onAction(OnboardingAction.SetHighCaloricFoods(false))
                        })
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Do you usually eat vegetables in your meal?",
            )
            Spacer(modifier = Modifier.height(8.dp))
            ToggleableAnswer(
                modifier = Modifier.fillMaxWidth(), state = arrayOf(
                    (AnswerState(isEnabled = state.vegetables == Vegetables.Yes,
                        label = "Always",
                        onAction = {
                            onAction(OnboardingAction.SetVegetables(Vegetables.Yes))
                        })),
                    AnswerState(isEnabled = state.vegetables == Vegetables.Maybe,
                        label = "Sometimes",
                        onAction = {
                            onAction(OnboardingAction.SetVegetables(Vegetables.Maybe))
                        }),
                    AnswerState(isEnabled = state.vegetables == Vegetables.No,
                        label = "Never",
                        onAction = {
                            onAction(OnboardingAction.SetVegetables(Vegetables.No))
                        })
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Do you eat between meals?",
            )
            Spacer(modifier = Modifier.height(8.dp))
            ToggleableAnswer(
                modifier = Modifier.fillMaxWidth(), state = arrayOf(
                    (AnswerState(isEnabled = state.foodBetweenMeals == FoodBetweenMeals.Always,
                        label = "Always",
                        onAction = {
                            onAction(OnboardingAction.SetFoodBetweenMeals(FoodBetweenMeals.Always))
                        })),
                    AnswerState(isEnabled = state.foodBetweenMeals == FoodBetweenMeals.Often,
                        label = "Often",
                        onAction = {
                            onAction(OnboardingAction.SetFoodBetweenMeals(FoodBetweenMeals.Often))
                        }),
                    AnswerState(isEnabled = state.foodBetweenMeals == FoodBetweenMeals.Sometimes,
                        label = "Sometimes",
                        onAction = {
                            onAction(OnboardingAction.SetFoodBetweenMeals(FoodBetweenMeals.Sometimes))
                        }),
                    AnswerState(isEnabled = state.foodBetweenMeals == FoodBetweenMeals.No,
                        label = "Never",
                        onAction = {
                            onAction(OnboardingAction.SetFoodBetweenMeals(FoodBetweenMeals.No))
                        })
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "How many main meals do you eat daily?",
            )
            Spacer(modifier = Modifier.height(8.dp))
            ToggleableAnswer(
                modifier = Modifier.fillMaxWidth(), state = arrayOf(
                    (AnswerState(isEnabled = state.mainMeal == MainMeal.Three,
                        label = "3",
                        onAction = {
                            onAction(OnboardingAction.SetMainMeal(MainMeal.Three))
                        })),
                    AnswerState(isEnabled = state.mainMeal == MainMeal.OneOrTwo,
                        label = "1-2",
                        onAction = {
                            onAction(OnboardingAction.SetMainMeal(MainMeal.OneOrTwo))
                        }),
                    AnswerState(isEnabled = state.mainMeal == MainMeal.MoreThanThree,
                        label = ">3",
                        onAction = {
                            onAction(OnboardingAction.SetMainMeal(MainMeal.MoreThanThree))
                        })
                )
            )
        }
    }
}

