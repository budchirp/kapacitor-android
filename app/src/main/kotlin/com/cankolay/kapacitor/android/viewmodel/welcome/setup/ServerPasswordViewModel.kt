package com.cankolay.kapacitor.android.viewmodel.welcome.setup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cankolay.kapacitor.android.data.datastore.AppDataStore
import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.remote.error.APIErrorReason
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.ApiResult.Error
import com.cankolay.kapacitor.android.data.remote.model.response.GetVersionEndpointResponse
import com.cankolay.kapacitor.android.data.remote.service.ApiTestService
import com.cankolay.kapacitor.android.ui.validation.ValidateData
import com.cankolay.kapacitor.android.ui.validation.ValidationError
import com.cankolay.kapacitor.android.ui.validation.model.ServerPasswordModel
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerPasswordSubmitReturn.Fail
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerPasswordSubmitReturn.InvalidCredentials
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerPasswordSubmitReturn.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

enum class ServerPasswordSubmitReturn {
    Success,
    Fail,
    InvalidCredentials
}

@HiltViewModel
class ServerPasswordViewModel @Inject constructor(
    private val serverDataStore: ServerDataStore,
    private val appDataStore: AppDataStore,
    private val apiTestService: ApiTestService
) :
    ViewModel() {
    var isLoading by mutableStateOf(value = false)
        private set

    var data by mutableStateOf(value = ServerPasswordModel())
        private set

    var validationError: ValidationError? by mutableStateOf(value = null)
        private set

    fun validateData(validationData: ServerPasswordModel = data): ValidationError? {
        validationError =
            ValidateData(model = ServerPasswordModel::class).validate(validationData)

        return validationError
    }

    fun updateData(newData: ServerPasswordModel) {
        validateData(
            validationData = newData
        )

        data = newData
    }

    suspend fun submit(): ServerPasswordSubmitReturn {
        if (validateData() != null) {
            return InvalidCredentials
        }

        try {
            isLoading = true

            val result: ApiResult<GetVersionEndpointResponse> = runBlocking {
                apiTestService.getVersion(
                    serverPassword = data.password
                )
            }


            if (result is ApiResult.Success) {
                viewModelScope.launch {
                    serverDataStore.flow.collect { state ->
                        serverDataStore.update(
                            state = state.copy(
                                serverPassword = data.password
                            )
                        )
                    }

                    appDataStore.flow.collect { state ->
                        appDataStore.update(
                            state = state.copy(
                                isSetupDone = true
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