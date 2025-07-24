package com.cankolay.kapacitor.android.data.remote.model.response.user

import com.cankolay.kapacitor.android.data.remote.model.response.user.profile.ProfileDto
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,

    val username: String,

    val profileDto: ProfileDto
)