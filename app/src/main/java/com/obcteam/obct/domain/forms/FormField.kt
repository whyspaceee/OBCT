package com.obcteam.obct.domain.forms

import androidx.compose.runtime.Immutable

@Immutable
data class FormField(
    val name: String,
    val text: String = "",
    val error: String? = null,
    val label: String,
    val validators: Validator
) {
    fun validate(): FormField {
        return copy(error = validators.validate(text))
    }
}