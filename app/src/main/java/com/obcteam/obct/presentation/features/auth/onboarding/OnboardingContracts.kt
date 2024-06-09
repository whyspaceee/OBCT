package com.obcteam.obct.presentation.features.auth.onboarding

import android.net.Uri
import com.obcteam.obct.domain.forms.FormField

data class OnboardingState(
    val imageUri: Uri? = null,
    val nameField: FormField,
    val dobField: FormField,
    val dobMilis: Long? = null,
    val isDobVisible: Boolean = false,
    val gender : Gender? = null
)

sealed class Gender(val value : String) {
    data object Female : Gender("female")
    data object Male : Gender("male")
}

sealed interface OnboardingAction {
    data class UpdateNameField(val name: String) : OnboardingAction
    data object OpenDobPicker : OnboardingAction
    data object CloseDobPicker : OnboardingAction
    data class UpdateDobField(val dob: Long) : OnboardingAction
    data class ChangeGender(val gender: Gender) : OnboardingAction

    data class UpdateImageUri(val uri: Uri) : OnboardingAction
    data object ValidateWelcomeScreen : OnboardingAction

    data object SubmitRegister : OnboardingAction
}

sealed interface OnboardingSideEffect {
    data class NavigateTo(val screen: OnboardingScreen) : OnboardingSideEffect

}