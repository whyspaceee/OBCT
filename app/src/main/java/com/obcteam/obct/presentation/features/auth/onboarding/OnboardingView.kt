package com.obcteam.obct.presentation.features.auth.onboarding

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.obcteam.obct.R
import com.obcteam.obct.domain.mvi.unpack

@Composable
fun OnboardingView(
    modifier: Modifier = Modifier, vm: OnboardingViewModel,
) {
    val (state, onAction) = vm.unpack()

    OnboardingView(state = state, onAction = onAction)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingView(
    modifier: Modifier = Modifier,
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit
) {
    val onboardingNavController = rememberNavController()
    NavHost(
        navController = onboardingNavController, startDestination = OnboardingScreen.Welcome.route
    ) {
        composable(route = OnboardingScreen.Welcome.route) {
            val datePickerState = rememberDatePickerState()
            Scaffold { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(R.string.welcome))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.nameField.text,
                        label = { Text(text = state.nameField.label) },
                        isError = state.nameField.error != null,
                        supportingText = { state.nameField.error?.let { Text(text = it) } },
                        onValueChange = { onAction(OnboardingAction.UpdateNameField(it)) }
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerInput(Unit) {
                                awaitEachGesture {
                                    awaitFirstDown(pass = PointerEventPass.Initial)
                                    val upEvent =
                                        waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                    if (upEvent != null) {
                                        onAction(OnboardingAction.OpenDobPicker)
                                    }
                                }
                            },
                        label = { Text(text = state.dobField.label)},
                        value = state.dobField.text,
                        readOnly = true,
                        onValueChange = {},
                    )


                    if (state.isDobVisible) {
                        DatePickerDialog(
                            onDismissRequest = { onAction(OnboardingAction.CloseDobPicker) },
                            confirmButton = {
                                TextButton(onClick = {
                                    if (datePickerState.selectedDateMillis != null) {
                                        onAction(
                                            OnboardingAction.UpdateDobField(
                                                datePickerState.selectedDateMillis!!
                                            )
                                        )
                                        onAction(OnboardingAction.CloseDobPicker)
                                    }
                                }) {
                                    Text(text = stringResource(R.string.ok))
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { onAction(OnboardingAction.CloseDobPicker) }) {
                                    Text(text = stringResource(R.string.cancel))
                                }
                            }) {
                            DatePicker(
                                state = datePickerState
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /*TODO*/ }) {
                        Text(text = stringResource(R.string.cont))
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = stringResource(R.string.chose_the_wrong_account))
                    }
                }
            }
        }
    }
}

sealed class OnboardingScreen(val route: String) {
    data object Welcome : OnboardingScreen("welcome")
    data object Smoking : OnboardingScreen("smoking")
}
