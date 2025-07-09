package com.cankolay.kapacitor.android.data.remote.model.response.user.profile

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: Long,

    val name: String
)