package com.obcteam.obct.presentation.features.auth.onboarding.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
import com.obcteam.obct.R
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingAction
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingScreen
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val result =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) {
            it?.let {
                onAction(OnboardingAction.UpdateImageUri(it))
            }
        }
    Scaffold(modifier = modifier, bottomBar = {
        BottomAppBar(
            contentPadding = PaddingValues(horizontal = 16.dp),
            containerColor = Color.Transparent
        ) {
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                navController.navigate(OnboardingScreen.Gender.route)
            }) {
                Text(text = stringResource(R.string.cont))
            }
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.welcome),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(

            ) {
                Box(modifier = Modifier.padding(8.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(state.imageUri)
                            .crossfade(true).build(),
                        contentDescription = stringResource(R.string.profile_picture),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(128.dp)
                    )
                }

                FilledIconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = {
                        result.launch(PickVisualMediaRequest())
                    }) {
                    Icon(Icons.Filled.Edit, contentDescription = null)
                }

            }
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                value = state.nameField.text,
                label = { Text(text = state.nameField.label) },
                isError = state.nameField.error != null,
                maxLines = 1,
                supportingText = { state.nameField.error?.let { Text(text = it) } },
                onValueChange = { onAction(OnboardingAction.UpdateNameField(it)) })

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        awaitEachGesture {
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null) {
                                onAction(OnboardingAction.OpenDobPicker)
                            }
                        }
                    },
                shape = CircleShape,

                label = { Text(text = state.dobField.label) },
                value = state.dobField.text,
                maxLines = 1,
                readOnly = true,
                onValueChange = {},
            )


            if (state.isDobVisible) {
                DatePickerDialog(onDismissRequest = { onAction(OnboardingAction.CloseDobPicker) },
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

            TextButton(onClick = {
                FirebaseAuth.getInstance().signOut()
            }) {
                Text(text = stringResource(R.string.chose_the_wrong_account))
            }
        }
    }
}