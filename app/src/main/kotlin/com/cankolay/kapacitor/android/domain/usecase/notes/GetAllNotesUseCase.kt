package com.cankolay.kapacitor.android.domain.usecase.notes

import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.notes.GetAllNotesResponse
import com.cankolay.kapacitor.android.data.remote.service.NotesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllNotesUseCase
@Inject
constructor(
    private val notesService: NotesService
) {
    suspend operator fun invoke(): ApiResult<GetAllNotesResponse> {
        return try {
            val result = withContext(context = Dispatchers.IO) {
                notesService.getAll()
            }

            result
        } catch (_: Exception) {
            ApiResult.Fatal(Throwable())
        }
    }
}
