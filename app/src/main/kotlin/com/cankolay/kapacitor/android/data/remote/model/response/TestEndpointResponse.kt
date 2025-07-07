package com.cankolay.kapacitor.android.data.remote.model.response

import com.cankolay.kapacitor.android.data.remote.model.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class TestEndpointResponseBody(
    val status: String
)

@Serializable
data class TestEndpointResponse(
    override val message: String,
    override val messageCode: String,
    override val data: TestEndpointResponseBody
) : ApiResponse<TestEndpointResponseBody>