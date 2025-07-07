package com.cankolay.kapacitor.android.viewmodel.welcome.setup

import androidx.lifecycle.ViewModel
import com.cankolay.kapacitor.android.data.datastore.AppDataStore
import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.remote.model.APIErrorReason
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.ApiResult.Error
import com.cankolay.kapacitor.android.data.remote.service.ApiTestService
import com.cankolay.kapacitor.android.ui.validation.ValidateData
import com.cankolay.kapacitor.android.ui.validation.ValidationError
import com.cankolay.kapacitor.android.ui.validation.model.ServerPasswordModel
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerPasswordSubmitReturn.Fail
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerPasswordSubmitReturn.InvalidCredentials
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerPasswordSubmitReturn.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
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
    var isLoading = MutableStateFlow(value = false)
        private set

    var data = MutableStateFlow(value = ServerPasswordModel())
        private set

    var errors = MutableStateFlow<Map<String, Set<ValidationError>>>(value = emptyMap())
        private set

    fun validateData(validationData: ServerPasswordModel = data.value): Map<String, Set<ValidationError>> {
        errors.value =
            ValidateData(model = ServerPasswordModel::class).validate(validationData)

        return errors.value
    }

    fun updateData(newData: ServerPasswordModel) {
        validateData(
            validationData = newData
        )

        data.value = newData
    }

    suspend fun submit(): ServerPasswordSubmitReturn {
        if (validateData().isNotEmpty()) return InvalidCredentials

        try {
            isLoading.value = true

            val result =
                withContext(context = Dispatchers.IO) {
                    apiTestService.getVersion(
                        serverPassword = data.value.password
                    )
                }

            if (result is ApiResult.Success) {
                val serverState = serverDataStore.flow.first()
                serverDataStore.update(
                    state = serverState.copy(
                        serverPassword = data.value.password
                    )
                )

                val appState = appDataStore.flow.first()
                appDataStore.update(
                    state = appState.copy(
                        isSetupDone = true
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