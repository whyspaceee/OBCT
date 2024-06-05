package com.obcteam.obct.presentation.features.auth.onboarding

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obcteam.obct.domain.forms.FormField
import com.obcteam.obct.domain.forms.Validator
import com.obcteam.obct.domain.mvi.MVI
import com.obcteam.obct.domain.mvi.mvi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class OnboardingViewModel : ViewModel(),
    MVI<OnboardingState, OnboardingAction, OnboardingSideEffect> by mvi(
        OnboardingState(
            imageUri = "",
            nameField = FormField(
                name = "name", label = "What should we call you?", text = "", validators = Validator.RequiredValidator
            ),
            dobField = FormField(
                name = "dob",
                label = "Date of Birth",
                text = "",
                validators = Validator.RequiredValidator
            ),
        )
    ) {

    init {
        viewModelScope.launch {
            var lastDobMilis: Long? = null
            uiState.collectLatest {
                if (it.dobMilis != lastDobMilis && it.dobMilis != null) {
                    lastDobMilis = it.dobMilis
                    val locale =
                        ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration())
                            .get(0);
                    val timestampAsDateString =
                        SimpleDateFormat.getDateInstance().format(
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