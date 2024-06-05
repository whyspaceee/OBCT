package com.obcteam.obct.presentation.features.auth.onboarding

import com.obcteam.obct.domain.forms.FormField

data class OnboardingState(
    val imageUri: String? = null,
    val nameField: FormField,
    val dobField: FormField,
    val dobMilis: Long? = null,
    val isDobVisible: Boolean = false
)

sealed interface OnboardingAction {
    data class UpdateNameField(val name: String) : OnboardingAction
    data object OpenDobPicker : OnboardingAction
    data object CloseDobPicker : OnboardingAction
    data class UpdateDobField(val dob: Long) : OnboardingAction
}

sealed interface OnboardingSideEffect {
}