package com.cankolay.kapacitor.android.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val message: String,
    val messageCode: String,
    val status: String
)