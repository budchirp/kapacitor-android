package com.cankolay.kapacitor.android.presentation.validation.model.welcome.setup

import com.cankolay.kapacitor.android.presentation.validation.annotations.NotEmpty
import kotlinx.serialization.Serializable

@Serializable
data class ServerPasswordModel(
    @NotEmpty val password: String = ""
)
