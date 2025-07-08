package com.cankolay.kapacitor.android.ui.validation.validator

import com.cankolay.kapacitor.android.ui.validation.ValidationError

interface IValidator {
    fun validate(property: String, value: Any?): ValidationError?
}