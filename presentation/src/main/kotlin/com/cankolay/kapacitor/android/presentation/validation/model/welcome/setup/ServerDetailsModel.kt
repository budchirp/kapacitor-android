package com.cankolay.kapacitor.android.presentation.validation.model.welcome.setup

import com.cankolay.kapacitor.android.presentation.validation.annotations.IsNumber
import com.cankolay.kapacitor.android.presentation.validation.annotations.NotEmpty
import kotlinx.serialization.Serializable

@Serializable
data class ServerDetailsModel(
    @NotEmpty val url: String = "",

    @NotEmpty @IsNumber val port: String = "8080"
)