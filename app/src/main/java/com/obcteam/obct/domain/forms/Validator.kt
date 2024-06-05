package com.obcteam.obct.domain.forms

interface Validator {
    fun validate(email: String): String?
    object EmailValidator : Validator {
        override fun validate(email: String): String? {
            if (email.isEmpty()) return "Email is required"

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return "Invalid email"
            }

            return null
        }
    }

    object PasswordValidator : Validator {
        override fun validate(email: String): String? {
            if (email.isEmpty()) return "Password is required"

            if (email.length < 8) {
                return "Password must be at least 8 characters"
            }

            return null
        }
    }

    object RequiredValidator : Validator {
        override fun validate(email: String): String? {
            if (email.isEmpty()) return "This field is required"

            return null
        }
    }
}