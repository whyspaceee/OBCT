package com.obcteam.obct.presentation.features.auth.onboarding

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.obcteam.obct.R
import com.obcteam.obct.domain.mvi.unpack
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingAction
import com.obcteam.obct.presentation.features.auth.onboarding.screens.WelcomeScreen
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
            Scaffold(bottomBar = {
                BottomAppBar(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    containerColor = Color.Transparent
                ) {
                    Button(modifier = Modifier.fillMaxWidth(), onClick = {
                        onAction(OnboardingAction.SubmitRegister)
                    }) {
                        Text(text = stringResource(R.string.cont))
                    }
                }
            }) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.can_you_tell_us_which_gender_you_are),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        FilledIconToggleButton(
                            modifier = Modifier
                                .weight(1f)
                                .height(80.dp),
                            shape = RoundedCornerShape(25),
                            onCheckedChange = {
                                onAction(OnboardingAction.ChangeGender(Gender.Male))
                            },
                            checked = state.gender == Gender.Male,
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
                                onAction(OnboardingAction.ChangeGender(Gender.Female))
                            },
                            checked = state.gender == Gender.Female,
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
    data object Welcome : OnboardingScreen("welcome")
    data object Gender : OnboardingScreen("gender")
    data object Smoking : OnboardingScreen("smoking")
}
