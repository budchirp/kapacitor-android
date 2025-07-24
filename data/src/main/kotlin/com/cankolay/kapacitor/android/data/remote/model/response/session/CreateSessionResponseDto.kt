package com.cankolay.kapacitor.android.data.remote.model.response.session

import com.cankolay.kapacitor.android.data.remote.model.response.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionResponseBodyDto(
    val token: String
)

@Serializable
data class CreateSessionResponseDto(
    override val message: String,
    override val messageCode: String,
    override val data: CreateSessionResponseBodyDto
) : ApiResponse<CreateSessionResponseBodyDto>