package com.obcteam.obct.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingView
import com.obcteam.obct.presentation.features.auth.register.RegisterView
import com.obcteam.obct.presentation.features.auth.register.RegisterViewModel

object Graph {
    const val ROOT = "root"
    const val MAIN = "main"
    const val AUTH = "auth"
    const val REGISTER = "register"
    const val ONBOARD = "onboard"
}

@Composable
fun RootNavigationGraph(navHostController: NavHostController, startDestination: String) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = startDestination,
        enterTransition =  {
            fadeIn(animationSpec = tween(2000))
        }
    ) {
        authNavGraph(navHostController)
        composable(route = Graph.REGISTER) {
            val vm = hiltViewModel<RegisterViewModel>()
            RegisterView(
                vm = vm,
            )
        }
        composable(route = Graph.ONBOARD) {
            val vm = hiltViewModel<RegisterViewModel>()
            OnboardingView()
        }

        composable(
            route = Graph.MAIN
        ) {
            NavigationScaffold()
        }

    }
}