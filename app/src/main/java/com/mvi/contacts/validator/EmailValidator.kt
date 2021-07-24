package com.mvi.contacts.validator

import android.widget.EditText

class EmailValidator : Validator() {
    override fun isValid(editText: EditText): Boolean {
        return editText.text.toString().isNotEmpty() && editText.text.toString()
            .matches(Regex(REGEX_EMAIL))
    }
}