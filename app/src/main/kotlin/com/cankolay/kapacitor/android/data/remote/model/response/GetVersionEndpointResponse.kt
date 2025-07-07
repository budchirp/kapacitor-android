package com.cankolay.kapacitor.android.data.remote.model.response

import com.cankolay.kapacitor.android.data.remote.model.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetVersionEndpointResponseBody(
    val version: String
)

@Serializable
data class GetVersionEndpointResponse(
    override val message: String,
    override val messageCode: String,
    override val data: GetVersionEndpointResponseBody,
) : ApiResponse<GetVersionEndpointResponseBody>