package com.obcteam.obct.presentation.features.auth.onboarding.screens

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
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.obcteam.obct.R
import com.obcteam.obct.presentation.features.auth.onboarding.Gender
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingAction
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingState

@Composable
fun GenderScreen(modifier: Modifier = Modifier, state: OnboardingState, onAction: (OnboardingAction) -> Unit) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(horizontal = 32.dp),
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