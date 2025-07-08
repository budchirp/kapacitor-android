package com.cankolay.kapacitor.android.data.remote.model.response.user

import com.cankolay.kapacitor.android.data.remote.model.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserResponse(
    override val message: String,
    override val messageCode: String,
    override val data: String = ""
) : ApiResponse<String>