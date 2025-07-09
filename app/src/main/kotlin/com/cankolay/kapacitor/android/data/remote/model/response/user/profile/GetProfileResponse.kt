package com.cankolay.kapacitor.android.data.remote.model.response.user.profile

import com.cankolay.kapacitor.android.data.remote.model.ApiResponse
import com.cankolay.kapacitor.android.data.remote.model.response.user.User
import kotlinx.serialization.Serializable

@Serializable
data class GetProfileResponse(
    override val message: String,
    override val messageCode: String,
    override val data: User,
) : ApiResponse<User>