package com.cankolay.kapacitor.android.ui.validation.model

import com.cankolay.kapacitor.android.ui.validation.annotations.NotEmpty

data class ServerPasswordModel(
    @NotEmpty val password: String = ""
)
