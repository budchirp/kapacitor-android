package com.cankolay.kapacitor.android.data.remote.model.response.user.profile

import com.cankolay.kapacitor.android.data.remote.model.response.ApiResponse
import com.cankolay.kapacitor.android.data.remote.model.response.user.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class GetProfileResponseDto(
    override val message: String,
    override val messageCode: String,
    override val data: UserDto,
) : ApiResponse<UserDto>