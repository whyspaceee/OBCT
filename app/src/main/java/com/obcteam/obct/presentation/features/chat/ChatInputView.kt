package com.obcteam.obct.presentation.features.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ChatInputView(modifier: Modifier = Modifier, isFirstTime: Boolean) {
    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            ChatWidget(message = "Hi!")
            ChatWidget(message = "Welcome to our app!", delay = 1000)
            ChatWidget(message = "Hi!", delay = 2000)
        }
    }
}

@Composable
fun ChatWidget(
    modifier: Modifier = Modifier,
    isFromUser: Boolean = false,
    message: String = "",
    delay: Int =0
) {


    AnimatedVisibility(
        visibleState = MutableTransitionState(false).apply { targetState = true },
        enter = fadeIn(TweenSpec(delay = delay))
    ) {
        Box(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        ) {
            ChatBubble(
                modifier = Modifier.then(
                    if (isFromUser) {
                        Modifier.align(Alignment.CenterEnd)
                    } else {
                        Modifier.align(Alignment.CenterStart)
                    }
                ), isFromUser = isFromUser, message = message
            )
        }
    }
}


@Composable
fun ChatBubble(modifier: Modifier = Modifier, message: String = "", isFromUser: Boolean = false) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        Text(
            text = message
        )
    }
}
