package com.obcteam.obct.domain.forms

interface Validator  {
    fun validate(string: String): String?
    object EmailValidator : Validator {
        override fun validate(string: String): String? {
            if (string.isEmpty()) return "Email is required"

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()) {
                return "Invalid email"
            }

            return null
        }
    }

    object PasswordValidator : Validator {
        override fun validate(string: String): String? {
            if (string.isEmpty()) return "Password is required"

            if (string.length < 8) {
                return "Password must be at least 8 characters"
            }

            return null
        }
    }

    object RequiredValidator : Validator {
        override fun validate(string: String): String? {
            if (string.isEmpty()) return "This field is required"

            return null
        }
    }
}