package com.cankolay.kapacitor.android.presentation.viewmodel.welcome.setup

import androidx.lifecycle.ViewModel
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.server.GetVersionResponseDto
import com.cankolay.kapacitor.android.domain.usecase.server.GetVersionUseCase
import com.cankolay.kapacitor.android.presentation.validation.ValidateData
import com.cankolay.kapacitor.android.presentation.validation.ValidationError
import com.cankolay.kapacitor.android.presentation.validation.model.welcome.setup.ServerPasswordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class ServerPasswordViewModel @Inject constructor(
    private val submitPasswordUseCase: GetVersionUseCase
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
        data.value = newData
    }

    suspend fun submit(): ApiResult<GetVersionResponseDto> {
        isLoading.value = true

        val result = submitPasswordUseCase(password = data.value.password)

        isLoading.value = false
        return result
    }

}