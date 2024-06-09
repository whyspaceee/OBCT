package com.obcteam.obct.presentation.features.auth.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obcteam.obct.domain.forms.FormField
import com.obcteam.obct.domain.forms.Validator
import com.obcteam.obct.domain.mvi.MVI
import com.obcteam.obct.domain.mvi.mvi
import com.obcteam.obct.domain.repository.AuthRepository
import com.obcteam.obct.domain.repository.OBCTRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authRepository: AuthRepository, private val obctRepository: OBCTRepository
) : ViewModel(), MVI<OnboardingState, OnboardingAction, OnboardingSideEffect> by mvi(run {
    val currentUser = authRepository.getCurrentUser()
    OnboardingState(
        imageUri = currentUser?.photoUrl,
        nameField = FormField(
            name = "name",
            label = "What should we call you?",
            text = "${currentUser?.displayName}",
            validators = Validator.RequiredValidator
        ),
        dobField = FormField(
            name = "dob",
            label = "Date of Birth",
            text = "",
            validators = Validator.RequiredValidator
        ),
    )
}) {

    init {
        viewModelScope.launch {
            var lastDobMilis: Long? = null
            uiState.collectLatest {
                if (it.dobMilis != lastDobMilis && it.dobMilis != null) {
                    lastDobMilis = it.dobMilis
                    val timestampAsDateString = SimpleDateFormat.getDateInstance().format(
                        it.dobMilis
                    )
                    updateUiState {
                        copy(
                            dobField = dobField.copy(
                                text = timestampAsDateString
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onAction(uiAction: OnboardingAction) {
        when (uiAction) {
            is OnboardingAction.UpdateNameField -> updateNameField(uiAction.name)
            is OnboardingAction.OpenDobPicker -> updateUiState { copy(isDobVisible = true) }
            is OnboardingAction.CloseDobPicker -> updateUiState { copy(isDobVisible = false) }
            is OnboardingAction.UpdateDobField -> updateUiState { copy(dobMilis = uiAction.dob) }
            is OnboardingAction.ChangeGender -> updateUiState { copy(gender = uiAction.gender) }
            is OnboardingAction.ValidateWelcomeScreen -> {
//                updateUiState { copy(nameField = nameField.validate()) }
            }

            is OnboardingAction.UpdateImageUri -> updateUiState { copy(imageUri = uiAction.uri) }
            is OnboardingAction.SubmitRegister -> submitRegister()
        }
    }

    private fun submitRegister() {
        val name = uiState.value.nameField.validate()
        val dob = uiState.value.dobMilis
        val gender = uiState.value.gender

        if (name.error != null) {
            println("Name is invalid")
            return
        }

        if (dob == null) {
            println(" dob is invalid")
            return
        }

        if (gender == null) {
            println(" gender is invalid")
            return
        }

        val dateIsoString = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(dob)

        println("DateIsoString $dateIsoString")

        viewModelScope.launch {
            try {
                obctRepository.register(
                    dateOfBirth = dateIsoString,
                    gender = gender.value
                )
            } catch (e: Exception) {
                println(e.message)
            }
        }


    }


    private fun updateNameField(name: String) {
        updateUiState {
            copy(
                nameField = nameField.copy(
                    text = name,
                )
            )
        }
    }


}