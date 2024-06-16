package com.obcteam.obct.presentation.features.auth.register

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
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository, private val obctRepository: OBCTRepository
) : ViewModel(), MVI<RegisterState, RegisterAction, RegisterSideEffect> by mvi(run {
    val currentUser = authRepository.getCurrentFirebaseUser()
    RegisterState(
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

    override fun onAction(uiAction: RegisterAction) {
        when (uiAction) {
            is RegisterAction.UpdateNameField -> updateNameField(uiAction.name)
            is RegisterAction.OpenDobPicker -> updateUiState { copy(isDobVisible = true) }
            is RegisterAction.CloseDobPicker -> updateUiState { copy(isDobVisible = false) }
            is RegisterAction.UpdateDobField -> updateUiState { copy(dobMilis = uiAction.dob) }
            is RegisterAction.ChangeGender -> updateUiState { copy(gender = uiAction.gender) }
            is RegisterAction.ValidateWelcomeScreen -> {
//                updateUiState { copy(nameField = nameField.validate()) }
            }

            is RegisterAction.UpdateImageUri -> updateUiState { copy(imageUri = uiAction.uri) }
            is RegisterAction.SubmitRegister -> submitRegister()
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
                authRepository.register(
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