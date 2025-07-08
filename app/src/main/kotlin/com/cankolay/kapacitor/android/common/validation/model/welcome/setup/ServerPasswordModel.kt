package com.cankolay.kapacitor.android.common.validation.model.welcome.setup

import com.cankolay.kapacitor.android.common.validation.annotations.NotEmpty

data class ServerPasswordModel(
    @NotEmpty val password: String = ""
)
