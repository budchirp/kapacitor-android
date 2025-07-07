package com.cankolay.kapacitor.android.viewmodel.welcome.setup

import androidx.lifecycle.ViewModel
import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.remote.model.APIErrorReason
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
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
    var isLoading = MutableStateFlow(value = false)
        private set

    var data = MutableStateFlow(value = ServerDetailsModel())
        private set

    var errors = MutableStateFlow<Map<String, Set<ValidationError>>>(value = emptyMap())
        private set

    fun validateData(validationData: ServerDetailsModel = data.value): Map<String, Set<ValidationError>> {
        errors.value =
            ValidateData(model = ServerDetailsModel::class).validate(validationData)

        return errors.value
    }

    fun updateData(newData: ServerDetailsModel) {
        validateData(
            validationData = newData
        )

        data.value = newData
    }

    suspend fun submit(): ServerDetailsSubmitReturn {
        if (validateData().isNotEmpty()) return InvalidCredentials

        try {
            isLoading.value = true

            val result = withContext(Dispatchers.IO) {
                apiTestService.test(
                    serverUrl = data.value.url,
                    serverPort = data.value.port.toInt()
                )
            }

            if (result is ApiResult.Success) {
                val serverState = serverDataStore.flow.first()
                serverDataStore.update(
                    state = serverState.copy(
                        serverUrl = data.value.url,
                        serverPort = data.value.port.toInt()
                    )
                )
            }

            isLoading.value = false

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