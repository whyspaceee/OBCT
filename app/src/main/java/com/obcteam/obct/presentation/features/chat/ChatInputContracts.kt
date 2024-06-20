package com.obcteam.obct.presentation.features.chat

import androidx.compose.runtime.Immutable
import com.obcteam.obct.presentation.features.auth.onboarding.OnboardingState

data class ChatBubbleModel(
    val message: String,
    val isFromUser: Boolean = false,
    val responseType: ResponseType? = null,
    val isFromLLM : Boolean = false
)

sealed interface ResponseType {
    object ChangeValue : ResponseType
    object Chat : ResponseType
}
@Immutable
data class ChatInputState(
    val messages : List<ChatBubbleModel> = emptyList(),
    val isLoading : Boolean = false,
    val textField : String? = null
)

sealed interface ValueTypes {
    data class Weight(val weight: Int) : ValueTypes
    data class Height(val height: Int) : ValueTypes
    data class EatingHabits(val eatingHabits: OnboardingState) : ValueTypes
    data class DailyHabits(val dailyHabits: OnboardingState) : ValueTypes

}

sealed interface  ChatInputSideEffect{

}

sealed interface ChatInputAction {
    data class AddMessage(val message: ChatBubbleModel) : ChatInputAction
    data class ChangeValue(val value: ValueTypes) : ChatInputAction
}