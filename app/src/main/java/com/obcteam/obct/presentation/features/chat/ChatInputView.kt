package com.obcteam.obct.presentation.features.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.arnyminerz.markdowntext.MarkdownText
import com.obcteam.obct.MainViewModel
import com.obcteam.obct.domain.mvi.unpack
import com.obcteam.obct.presentation.navigation.Graph

@Composable
fun ChatInputView(
    modifier: Modifier = Modifier,
    viewModel: ChatInputViewModel,
    mainViewModel: MainViewModel,
    rootNavController: NavHostController
) {
    val (state) = viewModel.unpack()


    ChatInputView(
        modifier = modifier, state = state, vm = viewModel, rootNavController = rootNavController
    )


}

@Composable
fun ChatInputView(
    modifier: Modifier = Modifier,
    state: ChatInputState,
    vm: ChatInputViewModel,
    rootNavController: NavHostController
) {
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = state) {
        scrollState.animateScrollToItem(state.messages.size - 1)
    }

    Scaffold(bottomBar = {
        if (state.messages.last().responseType is ResponseType.Chat) {
            AnimatedVisibility(
                modifier = modifier.fillMaxWidth(), visibleState = MutableTransitionState(
                    state.textField != null
                ).apply {
                    targetState = true
                }, enter = fadeIn(TweenSpec(delay = 2000))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = state.textField ?: "",
                        onValueChange = {
                            vm.setTextField(it)
                        },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors().copy(

                        ),
                        shape = CircleShape,
                        textStyle = MaterialTheme.typography.bodyMedium,

                        )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { vm.generate() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = ""
                        )
                    }
                }
            }
        }
    }) { paddingValues ->
        LazyColumn(
            state = scrollState,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (state.isLoading) {
                item {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            } else {
                items(state.messages.size) {
                    val message = state.messages[it]
                    ChatWidget(
                        isFromUser = message.isFromUser,
                        message = message.message,
                        isFromLLM = message.isFromLLM,
                        delay = it * 200
                    )

                }
                if (state.messages.last().responseType is ResponseType.ChangeValue) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        QuestionWidget(
                            modifier = Modifier.fillMaxWidth(),
                            delay = state.messages.size * 300,
                            onClickNo = {
                                vm.sendLastData()
                            },
                            onClickYes = {
                                rootNavController.navigate(Graph.ONBOARD)
                            },
                            state = state
                        )
                    }
                }


            }
        }
    }
}


@Composable
fun QuestionWidget(
    modifier: Modifier = Modifier,
    delay: Int,
    onClickNo: () -> Unit,
    onClickYes: () -> Unit,
    state: ChatInputState
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {

        Column(
            modifier = Modifier.align(Alignment.CenterEnd).width(200.dp)
        ) {
            OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {
                onClickYes()
            }) {
                Text(text = "Yes")
            }
            OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = { onClickNo() }) {
                Text(text = "No")
            }

        }
    }

}


@Composable
fun ChatWidget(
    modifier: Modifier = Modifier,
    isFromUser: Boolean = false,
    message: String = "",
    isFromLLM: Boolean,
    delay: Int = 0
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


@Composable
fun ChatBubble(modifier: Modifier = Modifier, message: String = "", isFromUser: Boolean = false) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isFromUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            )
            .padding(
                horizontal = 16.dp, vertical = 8.dp
            )
    ) {
        MarkdownText(
            markdown = message, style = MaterialTheme.typography.bodyLarge.copy(
                color = if (
                    isFromUser
                ) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

    }
}

sealed class ChatScreen(val route: String) {
    object Chat : ChatScreen("chat")
    object ChangeWeight : ChatScreen("change_weight")
    object ChangeHeight : ChatScreen("change_height")
    object ChangeEatingHabits : ChatScreen("change_eating_habits")
    object ChangeDailyHabits : ChatScreen("change_daily_habits")

}

