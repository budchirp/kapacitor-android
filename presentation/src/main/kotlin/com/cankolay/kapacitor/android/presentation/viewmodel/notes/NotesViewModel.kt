package com.cankolay.kapacitor.android.presentation.viewmodel.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.notes.NoteDto
import com.cankolay.kapacitor.android.domain.usecase.notes.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getAllNoteUseCase: GetAllNotesUseCase
) :
    ViewModel() {
    var isLoading = MutableStateFlow(value = false)
        private set

    var notes: MutableStateFlow<List<NoteDto>> = MutableStateFlow(value = emptyList())
        private set

    suspend fun fetch() {
        isLoading.value = true

        val result = getAllNoteUseCase()
        if (result is ApiResult.Success) {
            notes.value = result.response.data
        }

        isLoading.value = false
    }

    init {
        viewModelScope.launch {
            fetch()
        }
    }
}