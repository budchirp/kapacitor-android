package com.cankolay.kapacitor.android.data.remote.model.response.server

import com.cankolay.kapacitor.android.data.remote.model.response.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class TestResponseBodyDto(
    val status: String
)

@Serializable
data class TestResponseDto(
    override val message: String,
    override val messageCode: String,
    override val data: TestResponseBodyDto
) : ApiResponse<TestResponseBodyDto>