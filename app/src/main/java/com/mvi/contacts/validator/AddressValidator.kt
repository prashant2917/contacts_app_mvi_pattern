package com.mvi.contacts.validator

import android.widget.EditText

class AddressValidator : Validator() {
    override fun isValid(editText: EditText): Boolean {
        return editText.text.toString().isNotEmpty()
    }
}