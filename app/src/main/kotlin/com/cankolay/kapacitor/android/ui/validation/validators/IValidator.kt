package com.cankolay.kapacitor.android.ui.validation.validators

import com.cankolay.kapacitor.android.ui.validation.ValidationError

interface IValidator {
    fun validate(property: String, value: Any?): ValidationError?
}