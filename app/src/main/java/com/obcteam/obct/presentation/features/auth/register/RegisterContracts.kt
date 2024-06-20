package com.obcteam.obct.presentation.features.auth.register

import android.net.Uri
import com.obcteam.obct.domain.forms.FormField

data class RegisterState(
    val imageUri: Uri? = null,
    val nameField: FormField,
    val dobField: FormField,
    val dobMilis: Long? = null,
    val isDobVisible: Boolean = false,
    val gender : Gender? = null,
    val isSubmitting : Boolean = false
)

sealed class Gender(val value : String) {
    data object Female : Gender("female")
    data object Male : Gender("male")
}

sealed interface RegisterAction {
    data class UpdateNameField(val name: String) : RegisterAction
    data object OpenDobPicker : RegisterAction
    data object CloseDobPicker : RegisterAction
    data class UpdateDobField(val dob: Long) : RegisterAction
    data class ChangeGender(val gender: Gender) : RegisterAction

    data class UpdateImageUri(val uri: Uri) : RegisterAction
    data object ValidateWelcomeScreen : RegisterAction

    data object SubmitRegister : RegisterAction
}

sealed interface RegisterSideEffect {

    object refreshAuth : RegisterSideEffect

}