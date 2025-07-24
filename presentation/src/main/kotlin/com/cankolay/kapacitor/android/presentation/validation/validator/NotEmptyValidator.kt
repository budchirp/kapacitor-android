package com.cankolay.kapacitor.android.presentation.validation.validator

import com.cankolay.kapacitor.android.presentation.validation.ValidationError

object NotEmptyValidator : IValidator {
    override fun validate(property: String, value: Any?): ValidationError? {
        if (value.toString().isEmpty()) {
            return ValidationError(
                property = property,
                message = "empty"
            )
        }

        return null
    }
}
