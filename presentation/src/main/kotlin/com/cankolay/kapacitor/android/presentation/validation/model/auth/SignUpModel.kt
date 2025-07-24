package com.cankolay.kapacitor.android.presentation.validation.model.auth

import com.cankolay.kapacitor.android.presentation.validation.annotations.MaxLength
import com.cankolay.kapacitor.android.presentation.validation.annotations.MinLength
import com.cankolay.kapacitor.android.presentation.validation.annotations.NotEmpty
import kotlinx.serialization.Serializable

@Serializable
data class SignUpModel(
    @NotEmpty @MinLength(length = 3) @MaxLength(length = 15) val username: String = "",

    @NotEmpty @MinLength(length = 8) @MaxLength(length = 4096) val password: String = "",
)