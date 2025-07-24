package com.cankolay.kapacitor.android.presentation.validation.model.auth

import com.cankolay.kapacitor.android.presentation.validation.annotations.NotEmpty
import kotlinx.serialization.Serializable

@Serializable
data class SignInModel(
    @NotEmpty val username: String = "",

    @NotEmpty val password: String = "",
)