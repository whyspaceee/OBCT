package com.obcteam.obct.presentation.features.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.obcteam.obct.data.remote.models.PredictionResponse
import com.obcteam.obct.domain.mvi.MVI
import com.obcteam.obct.domain.mvi.mvi
import com.obcteam.obct.domain.repository.OBCTRepository
import com.obcteam.obct.domain.utils.OBCTFormatters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatInputViewModel @Inject constructor(
    val obctRepository: OBCTRepository, val generativeModel: GenerativeModel
) : ViewModel(), MVI<ChatInputState, ChatInputAction, ChatInputSideEffect> by mvi(
    ChatInputState(
        messages = listOf(
            ChatBubbleModel("Hello! I'm your personal health assistant. How can I help you today?"),
            ChatBubbleModel("I can help you with your diet, exercise, and other health-related queries."),
            ChatBubbleModel("You can ask me anything!"),
        )
    )

) {

    private val _lastWeightPrediction = MutableStateFlow<PredictionResponse?>(null)
    val lastWeightPrediction = _lastWeightPrediction.asStateFlow()

    fun setTextField(text: String) {
        updateUiState {
            copy(
                textField = text
            )
        }
    }

    fun generate() {
        val prompt = uiState.value.textField
        updateUiState {
            copy(
                messages = messages + ChatBubbleModel(
                    prompt ?: "", isFromUser = true
                ) + ChatBubbleModel(
                    "", responseType = ResponseType.Chat
                ), textField = ""
            )

        }
        viewModelScope.launch {
            val messagesFromLLM = uiState.value.messages.filter {
                it.isFromLLM
            }.map {
                Content(role = "model", parts = listOf(TextPart(text = it.message)))
            }.toTypedArray()



            try {
                generativeModel.generateContentStream(
                    *messagesFromLLM,
                    Content(role = "user",
                        parts = listOf(TextPart(text = "Here is my recommendations from my doctor for weight management ${
                            lastWeightPrediction.value?.prediction?.recommendation?.map { it.recommendation }
                                ?.joinToString()
                        }"), TextPart(
                            text = "Now i want to ask, $prompt"
                        ))),


                    ).collectLatest {
                    updateUiState {
                        val lastMessage = this.messages.last()
                        copy(
                            messages = messages.dropLast(1) + lastMessage.copy(
                                message = lastMessage.message + " ${it.text}",
                                responseType = ResponseType.Chat,
                                isFromLLM = true
                            ),
                        )
                    }
                }
            } catch (e: Exception) {
                println(e.message)

            }
        }
    }

    fun sendLastData() {
        updateUiState {
            copy(
                messages = messages + ChatBubbleModel(
                    "No",
                    isFromUser = true
                ) + ChatBubbleModel(
                    "Is there anything else you would like to know?",
                    responseType = ResponseType.Chat
                )
            )
        }
    }

    private fun setLastWeightPrediction(predictionResponse: PredictionResponse) {
        _lastWeightPrediction.value = predictionResponse

        updateUiState {
            copy(messages = messages + ChatBubbleModel("Your last evaluation was on ${
                OBCTFormatters.formatRelativeDateFromISO(
                    predictionResponse.createdAt ?: ""
                )
            }. \n\n ${
                predictionResponse.prediction?.recommendation?.map { it.recommendation }
                    ?.joinToString()
            }") + ChatBubbleModel(
                "Did anything change since then?", responseType = ResponseType.ChangeValue
            ))

        }
    }

    init {
        updateUiState {
            copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            try {
                obctRepository.lastPrediction().let {
                    if (it != null) {
                        setLastWeightPrediction(it)
                    }
                }
                updateUiState {
                    copy(
                        isLoading = false
                    )
                }
            } catch (e: Exception) {

            }
        }
    }
}