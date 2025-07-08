package com.cankolay.kapacitor.android.data.remote.model.response.session

import com.cankolay.kapacitor.android.data.remote.model.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionResponseBody(
    val token: String
)

@Serializable
data class CreateSessionResponse(
    override val message: String,
    override val messageCode: String,
    override val data: CreateSessionResponseBody
) : ApiResponse<CreateSessionResponseBody>