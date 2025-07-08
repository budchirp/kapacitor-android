package com.cankolay.kapacitor.android.viewmodel.welcome.setup

import androidx.lifecycle.ViewModel
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.server.GetVersionResponse
import com.cankolay.kapacitor.android.domain.usecase.welcome.setup.SubmitServerPasswordUseCase
import com.cankolay.kapacitor.android.ui.validation.ValidateData
import com.cankolay.kapacitor.android.ui.validation.ValidationError
import com.cankolay.kapacitor.android.ui.validation.model.welcome.setup.ServerPasswordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class ServerPasswordViewModel @Inject constructor(
    private val submitPasswordUseCase: SubmitServerPasswordUseCase
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

    suspend fun submit(): ApiResult<GetVersionResponse> {
        isLoading.value = true

        val result = submitPasswordUseCase(password = data.value.password)

        isLoading.value = false
        return result
    }

}