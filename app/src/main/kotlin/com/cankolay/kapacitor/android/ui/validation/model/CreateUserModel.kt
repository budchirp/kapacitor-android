package com.cankolay.kapacitor.android.ui.validation.model

import com.cankolay.kapacitor.android.ui.validation.annotations.MaxLength
import com.cankolay.kapacitor.android.ui.validation.annotations.MinLength
import com.cankolay.kapacitor.android.ui.validation.annotations.NotEmpty

data class CreateUserModel(
    @NotEmpty @MinLength(length = 3) @MaxLength(length = 15) val username: String = "",

    @NotEmpty @MinLength(length = 8) @MaxLength(length = 4096) val password: String = "",
)
