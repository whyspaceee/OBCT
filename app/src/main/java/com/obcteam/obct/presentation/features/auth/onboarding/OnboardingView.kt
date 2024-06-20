package com.obcteam.obct.presentation.features.auth.onboarding

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.obcteam.obct.domain.mvi.CollectSideEffect
import com.obcteam.obct.domain.mvi.unpack
import com.obcteam.obct.presentation.features.auth.onboarding.screens.DailyHabitsScreen
import com.obcteam.obct.presentation.features.auth.onboarding.screens.FoodHabitsScreen
import com.obcteam.obct.presentation.features.auth.onboarding.screens.HeightScreen
import com.obcteam.obct.presentation.features.auth.onboarding.screens.WeightScreen
import com.obcteam.obct.presentation.navigation.Graph
import com.obcteam.obct.presentation.navigation.MainScreen

@Composable
fun OnboardingView(
    mainNavController: NavHostController,
    vm: OnboardingViewModel
) {
    val onboardingNavController = rememberNavController()
    val (state, onAction, sideEffect) = vm.unpack()

    CollectSideEffect(sideEffect = sideEffect) {
        when (it) {
            is OnboardingSideEffect.NavigateToMain -> {
                mainNavController.navigate(Graph.MAIN)
            }

            is OnboardingSideEffect.Navigate -> {
                onboardingNavController.navigate(it.route)
            }
        }
    }

    OnboardingView(
        onboardingNavController = onboardingNavController,
        state = state,
        onAction = onAction
    )
}

@Composable
fun OnboardingView(
    modifier: Modifier = Modifier,
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit,
    onboardingNavController: NavHostController
) {

    NavHost(modifier = modifier,
        navController = onboardingNavController,
        startDestination = OnboardingScreen.Weight.route,
        enterTransition = {
            fadeIn(tween(durationMillis = 2500))
        }) {
        composable(
            route = OnboardingScreen.Weight.route,
        ) {
            WeightScreen(
                state = state, onAction = onAction, navController = onboardingNavController
            )
        }
        composable(route = OnboardingScreen.Height.route) {
            HeightScreen(
                state = state, onAction = onAction, navController = onboardingNavController
            )
        }
        composable(route = OnboardingScreen.EatingHabits.route) {
            FoodHabitsScreen(state, onAction, onboardingNavController)
        }
        composable(route = OnboardingScreen.DailyHabits.route) {
            DailyHabitsScreen(
                onAction,
                state
            )
        }
    }
}


sealed class OnboardingScreen(val route: String) {
    object Height : OnboardingScreen("height")
    object Weight : OnboardingScreen("weight")
    object EatingHabits : OnboardingScreen("eating_habits")
    object DailyHabits : OnboardingScreen("daily_habits")

}

