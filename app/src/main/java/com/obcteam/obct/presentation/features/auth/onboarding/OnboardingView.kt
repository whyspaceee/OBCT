package com.obcteam.obct.presentation.features.auth.onboarding

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.obcteam.obct.R
import com.obcteam.obct.domain.mvi.unpack
import com.obcteam.obct.presentation.features.auth.onboarding.screens.HeightScreen
import com.obcteam.obct.presentation.features.auth.onboarding.screens.WeightScreen

@Composable
fun OnboardingView() {
    val onboardingNavController = rememberNavController()
    OnboardingView(onboardingNavController = onboardingNavController)
}

@Composable
fun OnboardingView(
    modifier: Modifier = Modifier,
//    state: RegisterState,
//    onAction: (RegisterAction) -> Unit,
    onboardingNavController: NavHostController
) {
    val vm = hiltViewModel<OnboardingViewModel>()
    val (state, onAction) = vm.unpack()

    NavHost(
        modifier = modifier,
        navController = onboardingNavController,
        startDestination = OnboardingScreen.Weight.route,
        enterTransition = {
            fadeIn(tween(durationMillis = 2500))
        }
    ) {
        composable(
            route = OnboardingScreen.Weight.route,
        ) {
            WeightScreen(
                state = state,
                onAction = onAction,
                navController = onboardingNavController
            )
        }
        composable(route = OnboardingScreen.Height.route) {
            HeightScreen(
                state = state,
                onAction = onAction,
                navController = onboardingNavController
            )
        }
        composable(route = OnboardingScreen.EatingHabits.route) {
            Scaffold { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Eating Habits",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        fontSize = 24.sp
                    )
                    Row {
                        FilledIconToggleButton(
                            modifier = Modifier
                                .weight(1f)
                                .height(80.dp),
                            shape = RoundedCornerShape(25),
                            onCheckedChange = {
                            },
                            checked = state.familyHistory == false,
                        ) {
                            Column {
                                Text(text = stringResource(R.string.male))
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        FilledIconToggleButton(
                            modifier = Modifier
                                .weight(1f)
                                .height(80.dp),
                            shape = RoundedCornerShape(25),
                            onCheckedChange = {
                            },
                            checked = state.familyHistory == true,
                        ) {
                            Column {
                                Text(text = stringResource(R.string.female))
                            }

                        }
                    }
                }
            }


        }
    }
}

sealed class OnboardingScreen(val route: String) {
    object Height : OnboardingScreen("height")
    object Weight : OnboardingScreen("weight")

    object EatingHabits : OnboardingScreen("eating_habits")

}

