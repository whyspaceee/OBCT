package com.obcteam.obct.presentation.features.auth.register

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
import com.obcteam.obct.presentation.features.auth.register.screens.GenderScreen
import com.obcteam.obct.presentation.features.auth.register.screens.WelcomeScreen
import com.obcteam.obct.presentation.features.chat.ChatInputView
import com.obcteam.obct.presentation.features.auth.register.RegisterAction as Action

@Composable
fun RegisterView(
    modifier: Modifier = Modifier, vm: RegisterViewModel,
) {
    val navController = rememberNavController()
    val (state, onAction, sideEffect) = vm.unpack()

    RegisterView(
        state = state,
        onAction = onAction,
        modifier = modifier,
        navController = navController
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterView(
    modifier: Modifier = Modifier,
    state: RegisterState,
    onAction: (Action) -> Unit,
    navController: NavHostController
) {
    NavHost(
        enterTransition = {
            slideInHorizontally()
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        navController = navController, startDestination = OnboardingScreen.Welcome.route
    ) {
        composable(
            route = OnboardingScreen.Welcome.route,
        ) {
            WelcomeScreen(
                state = state, onAction = onAction, navController = navController
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
