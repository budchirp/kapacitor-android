package com.cankolay.kapacitor.android.ui.validation.model

import com.cankolay.kapacitor.android.ui.validation.annotations.IsNumber
import com.cankolay.kapacitor.android.ui.validation.annotations.NotEmpty

data class ServerDetailsModel(
    @NotEmpty val url: String = "",

    @NotEmpty @IsNumber val port: String = "3000"
)