package com.cankolay.kapacitor.android.data.remote.model.response

import com.cankolay.kapacitor.android.data.remote.model.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class TestEndpointResponse(
    override val message: String,
    override val body: Boolean
) : ApiResponse<Boolean>