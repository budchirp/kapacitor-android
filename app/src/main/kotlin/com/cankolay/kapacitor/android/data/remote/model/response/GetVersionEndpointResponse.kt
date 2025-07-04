package com.cankolay.kapacitor.android.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Body(
    val version: String
)

@Serializable
data class GetVersionEndpointResponse(
    override val message: String,
    override val body: Body
) : com.cankolay.kapacitor.android.data.remote.model.ApiResponse<Body>