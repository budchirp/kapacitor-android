package com.cankolay.kapacitor.android.viewmodel.auth

import androidx.lifecycle.ViewModel
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.session.CreateSessionResponse
import com.cankolay.kapacitor.android.domain.usecase.auth.CreateSessionUseCase
import com.cankolay.kapacitor.android.ui.validation.ValidateData
import com.cankolay.kapacitor.android.ui.validation.ValidationError
import com.cankolay.kapacitor.android.ui.validation.model.auth.SignInModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val createSessionUseCase: CreateSessionUseCase
) :
    ViewModel() {
    var isLoading = MutableStateFlow(value = false)
        private set

    var data = MutableStateFlow(value = SignInModel())
        private set

    var errors = MutableStateFlow<Map<String, Set<ValidationError>>>(value = emptyMap())
        private set

    fun validateData(validationData: SignInModel = data.value): Map<String, Set<ValidationError>> {
        errors.value =
            ValidateData(model = SignInModel::class).validate(validationData)

        return errors.value
    }

    fun updateData(newData: SignInModel) {
        validateData(
            validationData = newData
        )

        data.value = newData
    }

    suspend fun submit(): ApiResult<CreateSessionResponse> {
        isLoading.value = true

        val result = createSessionUseCase(
            data = data.value
        )

        isLoading.value = false
        return result
    }
}