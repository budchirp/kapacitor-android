package com.cankolay.kapacitor.android.common.validation.model.welcome.setup

import com.cankolay.kapacitor.android.common.validation.annotations.IsNumber
import com.cankolay.kapacitor.android.common.validation.annotations.NotEmpty

data class ServerDetailsModel(
    @NotEmpty val url: String = "",

    @NotEmpty @IsNumber val port: String = "8080"
)