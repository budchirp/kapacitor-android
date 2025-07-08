package com.cankolay.kapacitor.android.data.remote.model.response.notes

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int,
    val title: String,
    val content: String,
)