package com.cankolay.kapacitor.android.domain.usecase.notes

import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.notes.GetNoteResponse
import com.cankolay.kapacitor.android.data.remote.service.NotesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNoteUseCase
@Inject
constructor(
    private val notesService: NotesService
) {
    suspend operator fun invoke(id: String): ApiResult<GetNoteResponse> {
        return try {
            val result = withContext(context = Dispatchers.IO) {
                notesService.get(id = id)
            }

            result
        } catch (_: Exception) {
            ApiResult.Fatal(Throwable())
        }
    }
}