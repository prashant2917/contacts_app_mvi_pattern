package com.mvi.contacts.validator


import com.mvi.contacts.validator.ValidatorType.ValidatorTypeName

object ValidatorFactory {
    fun getValidator(validatorType: ValidatorType): Validator {
        return when (validatorType) {
            is ValidatorTypeName -> {
                NameValidator()
            }
            is ValidatorType.ValidatorTypeMobile -> {
                MobileValidator()
            }
            is ValidatorType.ValidatorAddress -> {
                AddressValidator()
            }
            is ValidatorType.ValidatorEmail -> {
                EmailValidator()
            }
        }
    }
}