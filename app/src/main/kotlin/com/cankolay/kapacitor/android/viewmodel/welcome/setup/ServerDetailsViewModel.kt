package com.cankolay.kapacitor.android.viewmodel.welcome.setup

import androidx.lifecycle.ViewModel
import com.cankolay.kapacitor.android.common.validation.ValidateData
import com.cankolay.kapacitor.android.common.validation.ValidationError
import com.cankolay.kapacitor.android.common.validation.model.welcome.setup.ServerDetailsModel
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.TestResponse
import com.cankolay.kapacitor.android.domain.usecase.welcome.setup.TestServerConnectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ServerDetailsViewModel @Inject constructor(
    private val testServerConnectionUseCase: TestServerConnectionUseCase
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

    suspend fun submit(): ApiResult<TestResponse> {
        isLoading.value = true

        val result = testServerConnectionUseCase(
            url = data.value.url,
            port = data.value.port
        )

        isLoading.value = false
        return result
    }
}