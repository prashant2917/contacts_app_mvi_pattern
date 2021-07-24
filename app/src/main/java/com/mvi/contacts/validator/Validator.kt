package com.mvi.contacts.validator

import android.widget.EditText

abstract class Validator {
    fun validate(editText: EditText, errorMessage: String): Boolean {
        if (isValid(editText)) {
            return true
        } else {
            editText.isFocusable =true
            editText.error = errorMessage
            return false
        }
    }

    abstract fun isValid(editText: EditText): Boolean

    companion object {
        //regex
        const val REGEX_NAME = "[A-Z][a-z]*"
        const val REGEX_PHONE = "^\\d{10}$"
        const val REGEX_EMAIL = "^(.+)@(.+)$"
    }
}

sealed class ValidatorType {
    class ValidatorTypeName : ValidatorType()
    class ValidatorTypeMobile : ValidatorType()
    class ValidatorAddress : ValidatorType()
    class ValidatorEmail : ValidatorType()
}





