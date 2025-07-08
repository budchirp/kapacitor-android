package com.cankolay.kapacitor.android.viewmodel.notes

import androidx.lifecycle.ViewModel
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.notes.Note
import com.cankolay.kapacitor.android.domain.usecase.notes.GetNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase
) :
    ViewModel() {
    var isLoading = MutableStateFlow(value = false)
        private set

    var notes: MutableStateFlow<Note?> = MutableStateFlow(value = null)
        private set

    suspend fun fetch(id: String) {
        isLoading.value = true

        val result = getNoteUseCase(id)
        if (result is ApiResult.Success) {
            notes.value = result.response.data
        }

        isLoading.value = false
    }
}