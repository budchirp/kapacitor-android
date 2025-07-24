package com.cankolay.kapacitor.android.presentation.viewmodel.welcome.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.datastore.model.defaultServerState
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.server.TestResponseDto
import com.cankolay.kapacitor.android.domain.usecase.server.GetTestUseCase
import com.cankolay.kapacitor.android.presentation.validation.ValidateData
import com.cankolay.kapacitor.android.presentation.validation.ValidationError
import com.cankolay.kapacitor.android.presentation.validation.model.welcome.setup.ServerDetailsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ServerDetailsViewModel @Inject constructor(
    private val serverDataStore: ServerDataStore,
    private val getTestUseCase: GetTestUseCase
) :
    ViewModel() {
    var isLoading = MutableStateFlow(value = false)
        private set

    val serverStateFlow = serverDataStore.flow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = defaultServerState(),
    )

    var data = MutableStateFlow(
        value = ServerDetailsModel(
            url = serverStateFlow.value.url,
            port = serverStateFlow.value.port.toString()
        )
    )
        private set

    var errors = MutableStateFlow<Map<String, Set<ValidationError>>>(value = emptyMap())
        private set

    fun validateData(validationData: ServerDetailsModel = data.value): Map<String, Set<ValidationError>> {
        errors.value =
            ValidateData(model = ServerDetailsModel::class).validate(validationData)

        return errors.value
    }

    fun updateData(newData: ServerDetailsModel) {
        data.value = newData
    }

    suspend fun submit(): ApiResult<TestResponseDto> {
        isLoading.value = true

        val result = getTestUseCase(
            url = data.value.url,
            port = data.value.port
        )

        isLoading.value = false
        return result
    }
}