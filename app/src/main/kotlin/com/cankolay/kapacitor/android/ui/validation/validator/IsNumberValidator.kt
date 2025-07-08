package com.cankolay.kapacitor.android.ui.validation.validator

import com.cankolay.kapacitor.android.ui.validation.ValidationError

object IsNumberValidator : IValidator {
    override fun validate(property: String, value: Any?): ValidationError? {
        if (value.toString().toIntOrNull() == null) {
            return ValidationError(
                property = property,
                message = "not_number"
            )
        }

        return null
    }
}