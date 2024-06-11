package com.obcteam.obct.presentation.features.auth.onboarding

import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.obcteam.obct.domain.mvi.unpack
import com.obcteam.obct.presentation.features.auth.onboarding.screens.GenderScreen
import com.obcteam.obct.presentation.features.auth.onboarding.screens.WelcomeScreen
import com.obcteam.obct.presentation.features.chat.ChatInputView
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingAction as Action

@Composable
fun OnboardingView(
    modifier: Modifier = Modifier, vm: OnboardingViewModel,
) {
    val navController = rememberNavController()
    val (state, onAction, sideEffect) = vm.unpack()



    OnboardingView(
        state = state,
        onAction = onAction,
        modifier = modifier,
        onboardingNavController = navController
    )


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingView(
    modifier: Modifier = Modifier,
    state: OnboardingState,
    onAction: (Action) -> Unit,
    onboardingNavController: NavHostController
) {
    NavHost(
        enterTransition = {
            slideInHorizontally()
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        navController = onboardingNavController, startDestination = OnboardingScreen.Welcome.route
    ) {
        composable(
            route = OnboardingScreen.Welcome.route,
        ) {
            WelcomeScreen(
                state = state, onAction = onAction, navController = onboardingNavController
            )
        }
        composable(
            route = OnboardingScreen.Gender.route,
        ) {
            GenderScreen(state = state, onAction = onAction)
        }
        composable(route = OnboardingScreen.FirstTimeChat.route) {
            ChatInputView(isFirstTime = true)
        }
        composable(route = OnboardingScreen.FirstTimeInput.route) {
        }
    }
}


sealed class OnboardingScreen(val route: String) {
    data object Welcome : OnboardingScreen("welcome")
    data object Gender : OnboardingScreen("gender")
    data object FirstTimeChat : OnboardingScreen("first_time_chat")
    data object FirstTimeInput : OnboardingScreen("first_time_input")
}
