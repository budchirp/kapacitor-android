package com.cankolay.kapacitor.android.data.remote.model.response.server

import com.cankolay.kapacitor.android.data.remote.model.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetVersionResponseBody(
    val version: String
)

@Serializable
data class GetVersionResponse(
    override val message: String,
    override val messageCode: String,
    override val data: GetVersionResponseBody,
) : ApiResponse<GetVersionResponseBody>