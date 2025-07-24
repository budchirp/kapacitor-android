package com.cankolay.kapacitor.android.presentation.validation.validator

import com.cankolay.kapacitor.android.presentation.validation.ValidationError

interface IValidator {
    fun validate(property: String, value: Any?): ValidationError?
}