package com.cankolay.kapacitor.android.data.remote.model.response.notes

import com.cankolay.kapacitor.android.data.remote.model.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetNoteResponse(
    override val message: String,
    override val messageCode: String,
    override val data: Note,
) : ApiResponse<Note>