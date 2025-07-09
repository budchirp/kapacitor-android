package com.cankolay.kapacitor.android.data.remote.model.response.user

import com.cankolay.kapacitor.android.data.remote.model.response.user.profile.Profile
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,

    val username: String,

    val profile: Profile
)