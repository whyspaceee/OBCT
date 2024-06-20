package com.obcteam.obct.presentation.features.auth.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ToggleableAnswer(
    modifier: Modifier = Modifier,

    vararg state: AnswerState
) {

    FlowRow(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 2
    ) {
        state.map { answerState ->
            FilledIconToggleButton(
                modifier = Modifier
                    .height(80.dp)
                    .weight(1f),
                shape = RoundedCornerShape(25),
                onCheckedChange = {
                    answerState.onAction.invoke(it)
                },
                checked = answerState.isEnabled,
            ) {
                Column {
                    Text(text = answerState.label)
                }
            }
        }
    }

}

class AnswerState(val isEnabled: Boolean, val label: String, val onAction: (Boolean) -> Unit)

