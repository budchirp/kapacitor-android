package com.cankolay.kapacitor.android.ui.validation

import com.cankolay.kapacitor.android.ui.validation.annotations.IsNumber
import com.cankolay.kapacitor.android.ui.validation.annotations.NotEmpty
import com.cankolay.kapacitor.android.ui.validation.validators.IsNumberValidator
import com.cankolay.kapacitor.android.ui.validation.validators.NotEmptyValidator
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class ValidateData<T : Any>(
    val model: KClass<T>
) {
    private val validatorMap =
        mapOf(
            NotEmpty::class to NotEmptyValidator::validate,
            IsNumber::class to IsNumberValidator::validate
        )

    fun validate(state: T): Map<String, Set<ValidationError>> {
        val errorMap: MutableMap<String, Set<ValidationError>> = mutableMapOf()

        model.memberProperties.forEach { property: KProperty1<T, *> ->
            val errors: MutableSet<ValidationError> = mutableSetOf()

            property.annotations.forEach { annotation: Annotation ->
                val error: ValidationError? = try {
                    validatorMap[annotation.annotationClass]?.invoke(
                        property.name,
                        property.get(state)
                    )
                } catch (_: Exception) {
                    null
                }

                if (error != null) {
                    errors.add(error)
                }
            }

            if (errors.isNotEmpty()) {
                errorMap[property.name] = errors
            }
        }

        return errorMap
    }
}