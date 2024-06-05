package com.obcteam.obct.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.obcteam.obct.presentation.features.auth.login.LoginView
import com.obcteam.obct.presentation.features.auth.login.LoginViewModel
import kotlinx.coroutines.coroutineScope


fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTH, startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()

            LoginView(vm = loginViewModel)
        }
        composable(route = AuthScreen.Register.route) {


        }
    }

}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object Register : AuthScreen("register")
}