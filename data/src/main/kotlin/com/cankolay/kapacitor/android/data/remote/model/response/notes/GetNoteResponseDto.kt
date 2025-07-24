package com.cankolay.kapacitor.android.data.remote.model.response.notes

import com.cankolay.kapacitor.android.data.remote.model.response.ApiResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetNoteResponseDto(
    override val message: String,
    override val messageCode: String,
    override val data: NoteDto,
) : ApiResponse<NoteDto>