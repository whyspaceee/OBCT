package com.obcteam.obct.presentation.navigation

import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.obcteam.obct.R
import com.obcteam.obct.domain.forms.FormField
import com.obcteam.obct.domain.forms.Validator
import com.obcteam.obct.presentation.features.chat.ChatInputView

@Composable
fun MainNavGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    Box(modifier = modifier) {
        NavHost(
            navController = navController,
            route = Graph.MAIN,
            startDestination = MainScreen.Home.route
        ) {
            composable(route = MainScreen.Home.route) {
            }
            composable(route = MainScreen.Settings.route) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    Button(onClick = {
                        Firebase.auth.signOut()
                    }) {
                        Text(text = stringResource(R.string.sign_out))
                    }
                }
            }
            composable(route = MainScreen.AddData.route) {
                val heightField = FormField(
                    label = stringResource(R.string.height),
                    text = "",
                    name = "height",
                    error = null,
                    validators = Validator.RequiredValidator
                )
                val weightField = FormField(
                    label = stringResource(R.string.weight),
                    text = "",
                    name = "weight",
                    error = null,
                    validators = Validator.RequiredValidator
                )

                val offset = remember {
                    mutableFloatStateOf(0f)
                }

                val draggableState = rememberDraggableState {
                    offset.floatValue = it
                }


                Column(
                    modifier = Modifier.padding(32.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.width(256.dp),
                        shape = CircleShape,
                        value = heightField.text,
                        label = { Text(text = heightField.label) },
                        isError = heightField.error != null,
                        maxLines = 1,
                        trailingIcon = { Text(text = "cm") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        supportingText = { heightField.error?.let { Text(text = it) } },
                        onValueChange = { },
                    )
                    OutlinedTextField(
                        modifier = Modifier.width(256.dp),
                        shape = CircleShape,
                        value = weightField.text,
                        label = { Text(text = weightField.label) },
                        isError = weightField.error != null,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = { Text(text = "kg") },
                        supportingText = { weightField.error?.let { Text(text = it) } },
                        onValueChange = { },
                    )

                }
            }
            composable(route = MainScreen.Chat.route) {
                ChatInputView(isFirstTime = false)
            }


        }
    }

}

sealed class MainScreen(val route: String) {
    object Home : MainScreen("home")
    object Settings : MainScreen("settings")
    object AddData : MainScreen("add_data")
    object Chat : MainScreen("chat")

    fun withArgs(vararg args: String): String {
        return "$route/${args.joinToString("/")}"
    }
}
