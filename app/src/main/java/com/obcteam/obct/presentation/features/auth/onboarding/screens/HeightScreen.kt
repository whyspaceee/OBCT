package com.obcteam.obct.presentation.features.auth.onboarding.screens

import PickerStyle
import Scale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.obcteam.obct.R
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingAction
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingScreen
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingState

@Composable
fun HeightScreen(
    modifier: Modifier = Modifier,
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit,
    navController: NavHostController
) {
    Scaffold (
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(horizontal = 32.dp),
                containerColor = Color.Transparent
            ) {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    navController.navigate(OnboardingScreen.EatingHabits.route)
                }) {
                    Text(text = stringResource(R.string.cont))
                }
            }
        }
    ){ paddingValues ->
        Column(
            modifier = modifier.then(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Select Height", style = MaterialTheme.typography.headlineMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = state.height.toString(), fontWeight = FontWeight.Bold, fontSize = 64.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "cm", style = MaterialTheme.typography.headlineMedium)

            }
            Spacer(modifier = Modifier.height(40.dp))
            Box() {
                Scale(
                    pickerStyle = PickerStyle(
                        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                        minValue = 0,
                        maxValue = 250,
                        lineStroke = 12f,
                        initialValue = 165,
                        tenTypeLineHeight = 48,
                        fiveTypeLineHeight = 32,
                        numberPadding = 48,
                    )
                ) {
                    onAction(OnboardingAction.SetHeight(it))
                }
            }

        }
    }

}