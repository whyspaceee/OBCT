package com.obcteam.obct.presentation.navigation

import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingView
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingViewModel
import com.obcteam.obct.presentation.features.input.ChatInputView

object Graph {
    const val ROOT = "root"
    const val MAIN = "main"
    const val AUTH = "auth"
    const val ONBOARDING = "onboarding"
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
        composable(route = Graph.ONBOARDING) {
            val vm = hiltViewModel<OnboardingViewModel>()
            OnboardingView(
                vm = vm,
            )
        }
        composable(
            route = Graph.MAIN
        ) {
            NavigationScaffold()
        }

    }
}