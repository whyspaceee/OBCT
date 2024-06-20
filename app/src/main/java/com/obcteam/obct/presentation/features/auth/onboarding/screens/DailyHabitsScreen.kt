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
import androidx.compose.material3.CircularProgressIndicator
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
import com.obcteam.obct.R
import com.obcteam.obct.presentation.features.auth.onboarding.AlcoholConsumption
import com.obcteam.obct.presentation.features.auth.onboarding.DailyWaterIntake
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingAction
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingState
import com.obcteam.obct.presentation.features.auth.onboarding.PhysicalActivity
import com.obcteam.obct.presentation.features.auth.onboarding.TimeOnElectronicDevices
import com.obcteam.obct.presentation.features.auth.onboarding.Transportation
import com.obcteam.obct.presentation.features.auth.onboarding.components.AnswerState
import com.obcteam.obct.presentation.features.auth.onboarding.components.ToggleableAnswer

@Composable
fun DailyHabitsScreen(
    onAction: (OnboardingAction) -> Unit,
    state: OnboardingState
) {
    Scaffold(bottomBar = {
        BottomAppBar(
            contentPadding = PaddingValues(horizontal = 32.dp),
            containerColor = Color.Transparent
        ) {
            Button(modifier = Modifier.fillMaxWidth(),
                enabled = !state.isSubmitting,
                onClick = {
                    onAction(OnboardingAction.Submit)
                }) {
                if (state.isSubmitting) {
                    CircularProgressIndicator()
                } else {
                    Text(text = stringResource(R.string.cont))
                }
            }
        }
    }, topBar = {
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 64.dp)
                .fillMaxWidth(),
            text = "Daily Habits",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            fontSize = 24.sp
        )
    }) { paddingValues ->
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
                text = "Do you smoke?",
            )
            Spacer(modifier = Modifier.height(8.dp))
            ToggleableAnswer(
                modifier = Modifier.fillMaxWidth(), state = arrayOf(
                    (AnswerState(isEnabled = state.smoking == true,
                        label = "Yes",
                        onAction = {
                            onAction(OnboardingAction.SetSmoking(true))
                        })),
                    AnswerState(isEnabled = state.smoking == false,
                        label = "No",
                        onAction = {
                            onAction(OnboardingAction.SetSmoking(false))
                        })
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Do you monitor your daily calorie intake?",
            )
            Spacer(modifier = Modifier.height(8.dp))
            ToggleableAnswer(
                modifier = Modifier.fillMaxWidth(), state = arrayOf(
                    (AnswerState(isEnabled = state.monitorCalorieIntake == true,
                        label = "Yes",
                        onAction = {
                            onAction(OnboardingAction.SetMonitorCalorieIntake(true))
                        })),
                    AnswerState(isEnabled = state.monitorCalorieIntake == false,
                        label = "No",
                        onAction = {
                            onAction(OnboardingAction.SetMonitorCalorieIntake(false))
                        })
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "How often do you do physical activity?",
            )
            Spacer(modifier = Modifier.height(8.dp))
            ToggleableAnswer(
                modifier = Modifier.fillMaxWidth(), state = arrayOf(
                    (AnswerState(isEnabled = state.physicalActivity == PhysicalActivity.None,
                        label = "None",
                        onAction = {
                            onAction(OnboardingAction.SetPhysicalActivity(PhysicalActivity.None))
                        })),
                    AnswerState(isEnabled = state.physicalActivity == PhysicalActivity.OneTwo,
                        label = "1-2 times a week",
                        onAction = {
                            onAction(OnboardingAction.SetPhysicalActivity(PhysicalActivity.OneTwo))
                        }),
                    AnswerState(isEnabled = state.physicalActivity == PhysicalActivity.ThreeFour,
                        label = "3-4 times a week",
                        onAction = {
                            onAction(OnboardingAction.SetPhysicalActivity(PhysicalActivity.ThreeFour))
                        }),
                    AnswerState(isEnabled = state.physicalActivity == PhysicalActivity.FiveOrMore,
                        label = ">=5 times a week",
                        onAction = {
                            onAction(OnboardingAction.SetPhysicalActivity(PhysicalActivity.FiveOrMore))
                        })
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "How much water do you drink daily?",
            )
            Spacer(modifier = Modifier.height(8.dp))
            ToggleableAnswer(
                modifier = Modifier.fillMaxWidth(), state = arrayOf(
                    (AnswerState(isEnabled = state.dailyWaterIntake == DailyWaterIntake.LessThan1L,
                        label = "<1L",
                        onAction = {
                            onAction(OnboardingAction.SetDailyWaterIntake(DailyWaterIntake.LessThan1L))
                        })),
                    AnswerState(isEnabled = state.dailyWaterIntake == DailyWaterIntake.Between1And2L,
                        label = "1-2L",
                        onAction = {
                            onAction(OnboardingAction.SetDailyWaterIntake(DailyWaterIntake.Between1And2L))
                        }),
                    AnswerState(isEnabled = state.dailyWaterIntake == DailyWaterIntake.MoreThan2L,
                        label = ">2L",
                        onAction = {
                            onAction(OnboardingAction.SetDailyWaterIntake(DailyWaterIntake.MoreThan2L))
                        })
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "How often do you consume alcohol?",
            )
            Spacer(modifier = Modifier.height(8.dp))
            ToggleableAnswer(
                modifier = Modifier.fillMaxWidth(), state = arrayOf(
                    (AnswerState(isEnabled = state.alcoholConsumption == AlcoholConsumption.Never,
                        label = "Never",
                        onAction = {
                            onAction(OnboardingAction.SetAlcoholConsumption(AlcoholConsumption.Never))
                        })),
                    AnswerState(isEnabled = state.alcoholConsumption == AlcoholConsumption.Sometimes,
                        label = "Sometimes",
                        onAction = {
                            onAction(OnboardingAction.SetAlcoholConsumption(AlcoholConsumption.Sometimes))
                        }),
                    AnswerState(isEnabled = state.alcoholConsumption == AlcoholConsumption.Freq,
                        label = "Frequently",
                        onAction = {
                            onAction(OnboardingAction.SetAlcoholConsumption(AlcoholConsumption.Freq))
                        }),
                    AnswerState(isEnabled = state.alcoholConsumption == AlcoholConsumption.Always,
                        label = "Always",
                        onAction = {
                            onAction(OnboardingAction.SetAlcoholConsumption(AlcoholConsumption.Always))
                        })
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text("What is your time on electronic devices?")
            Spacer(modifier = Modifier.height(8.dp))
            ToggleableAnswer(
                modifier = Modifier.fillMaxWidth(), state = arrayOf(
                    (AnswerState(isEnabled = state.timeOnElectronicDevices == TimeOnElectronicDevices.LessThan2Hour,
                        label = "<2 hours",
                        onAction = {
                            onAction(
                                OnboardingAction.SetTimeOnElectronicDevices(
                                    TimeOnElectronicDevices.LessThan2Hour
                                )
                            )
                        })),
                    AnswerState(isEnabled = state.timeOnElectronicDevices == TimeOnElectronicDevices.Between3And5Hours,
                        label = "3-5 hours",
                        onAction = {
                            onAction(
                                OnboardingAction.SetTimeOnElectronicDevices(
                                    TimeOnElectronicDevices.Between3And5Hours
                                )
                            )
                        }),
                    AnswerState(isEnabled = state.timeOnElectronicDevices == TimeOnElectronicDevices.MoreThan5Hours,
                        label = ">5 hours",
                        onAction = {
                            onAction(
                                OnboardingAction.SetTimeOnElectronicDevices(
                                    TimeOnElectronicDevices.MoreThan5Hours
                                )
                            )
                        })
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text("What is your main transportation method?")
            Spacer(modifier = Modifier.height(8.dp))
            ToggleableAnswer(
                modifier = Modifier.fillMaxWidth(), state = arrayOf(
                    (AnswerState(isEnabled = state.transportation == Transportation.Bike,
                        label = "\uD83D\uDEB2 Bike",
                        onAction = {
                            onAction(OnboardingAction.SetTransportation(Transportation.Bike))
                        })),
                    AnswerState(isEnabled = state.transportation == Transportation.Motorbike,
                        label = "\uD83D\uDEF5 Motorbike",
                        onAction = {
                            onAction(OnboardingAction.SetTransportation(Transportation.Motorbike))
                        }),
                    AnswerState(isEnabled = state.transportation == Transportation.Walk,
                        label = "\uD83D\uDEB6\u200D♂\uFE0F Walk",
                        onAction = {
                            onAction(OnboardingAction.SetTransportation(Transportation.Walk))
                        }),
                    AnswerState(isEnabled = state.transportation == Transportation.Automobile,
                        label = "\uD83D\uDE97 Automobile",
                        onAction = {
                            onAction(OnboardingAction.SetTransportation(Transportation.Automobile))
                        }),
                    AnswerState(isEnabled = state.transportation == Transportation.PublicTransport,
                        label = "\uD83D\uDE88 Public Transport",
                        onAction = {
                            onAction(OnboardingAction.SetTransportation(Transportation.PublicTransport))
                        })
                )
            )

        }
    }
}