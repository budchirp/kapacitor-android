package com.cankolay.kapacitor.android.data.remote.model.response

import com.cankolay.kapacitor.android.data.remote.model.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserResponse(
    override val message: String,
    override val messageCode: String,
    override val data: Nothing
) : ApiResponse<Nothing>