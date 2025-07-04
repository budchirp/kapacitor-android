package com.cankolay.kapacitor.android.viewmodel.welcome.setup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.remote.error.APIErrorReason
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.ApiResult.Error
import com.cankolay.kapacitor.android.data.remote.service.ApiTestService
import com.cankolay.kapacitor.android.ui.validation.ValidateData
import com.cankolay.kapacitor.android.ui.validation.ValidationError
import com.cankolay.kapacitor.android.ui.validation.model.ServerDetailsModel
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerDetailsSubmitReturn.Fail
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerDetailsSubmitReturn.InvalidCredentials
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerDetailsSubmitReturn.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

enum class ServerDetailsSubmitReturn {
    Success,
    Fail,
    InvalidCredentials
}

@HiltViewModel
class ServerDetailsViewModel @Inject constructor(
    private val serverDataStore: ServerDataStore,
    private val apiTestService: ApiTestService
) :
    ViewModel() {
    var isLoading by mutableStateOf(value = false)
        private set

    var data by mutableStateOf(value = ServerDetailsModel())
        private set

    var validationError: ValidationError? by mutableStateOf(value = null)
        private set

    fun validateData(validationData: ServerDetailsModel = data): ValidationError? {
        validationError =
            ValidateData(model = ServerDetailsModel::class).validate(validationData)

        return validationError
    }

    fun updateData(newData: ServerDetailsModel) {
        validateData(
            validationData = newData
        )

        data = newData
    }

    suspend fun submit(): ServerDetailsSubmitReturn {
        if (validateData() != null) {
            return InvalidCredentials
        }

        try {
            val result = runBlocking {
                isLoading = true

                apiTestService.test(
                    serverUrl = data.url,
                    serverPort = data.port.toInt()
                )
            }

            if (result is ApiResult.Success) {
                viewModelScope.launch {
                    serverDataStore.flow.collect { state ->
                        serverDataStore.update(
                            state = state.copy(
                                serverUrl = data.url,
                                serverPort = data.port.toInt()
                            )
                        )
                    }
                }
            }

            isLoading = false

            return when (result) {
                is ApiResult.Success -> Success
                is Error -> when (result.reason) {
                    APIErrorReason.USER -> InvalidCredentials
                    APIErrorReason.SERVER -> Fail
                }

                is ApiResult.Fatal -> Fail
            }
        } catch (_: Exception) {
            return Fail
        }
    }
}