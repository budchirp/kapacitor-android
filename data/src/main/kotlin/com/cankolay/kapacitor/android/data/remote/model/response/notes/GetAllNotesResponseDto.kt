package com.cankolay.kapacitor.android.data.remote.model.response.notes

import com.cankolay.kapacitor.android.data.remote.model.response.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetAllNotesResponseDto(
    override val message: String,
    override val messageCode: String,
    override val data: List<NoteDto>,
) : ApiResponse<List<NoteDto>>