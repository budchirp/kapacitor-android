package com.cankolay.kapacitor.android.presentation.validation.model.settings

import com.cankolay.kapacitor.android.presentation.validation.annotations.IsNumber
import com.cankolay.kapacitor.android.presentation.validation.annotations.NotEmpty

data class ServerStateModel(
    @NotEmpty val url: String,
    @NotEmpty @IsNumber val port: String,
    @NotEmpty val password: String,
)