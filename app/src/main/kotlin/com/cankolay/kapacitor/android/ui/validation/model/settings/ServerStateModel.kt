package com.cankolay.kapacitor.android.ui.validation.model.settings

import com.cankolay.kapacitor.android.ui.validation.annotations.IsNumber
import com.cankolay.kapacitor.android.ui.validation.annotations.NotEmpty

data class ServerStateModel(
    @NotEmpty val url: String,
    @NotEmpty @IsNumber val port: String,
    @NotEmpty val password: String,
)