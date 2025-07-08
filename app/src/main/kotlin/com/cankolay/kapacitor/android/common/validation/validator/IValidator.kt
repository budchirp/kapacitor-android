package com.cankolay.kapacitor.android.common.validation.validator

import com.cankolay.kapacitor.android.common.validation.ValidationError

interface IValidator {
    fun validate(property: String, value: Any?): ValidationError?
}