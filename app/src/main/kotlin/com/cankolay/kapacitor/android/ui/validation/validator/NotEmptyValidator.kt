package com.cankolay.kapacitor.android.ui.validation.validator

import com.cankolay.kapacitor.android.ui.validation.ValidationError

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
