package com.cankolay.kapacitor.android.data.remote.model.response.notes

import com.cankolay.kapacitor.android.data.remote.model.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetAllNotesResponse(
    override val message: String,
    override val messageCode: String,
    override val data: List<Note>,
) : ApiResponse<List<Note>>