package com.cankolay.kapacitor.android.common.validation.model.auth

import com.cankolay.kapacitor.android.common.validation.annotations.MaxLength
import com.cankolay.kapacitor.android.common.validation.annotations.MinLength
import com.cankolay.kapacitor.android.common.validation.annotations.NotEmpty

data class CreateUserModel(
    @NotEmpty @MinLength(length = 3) @MaxLength(length = 15) val username: String = "",

    @NotEmpty @MinLength(length = 8) @MaxLength(length = 4096) val password: String = "",
)