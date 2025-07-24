package com.cankolay.kapacitor.android.presentation.viewmodel.auth

import androidx.lifecycle.ViewModel
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.session.CreateSessionResponseDto
import com.cankolay.kapacitor.android.domain.usecase.auth.CreateSessionUseCase
import com.cankolay.kapacitor.android.presentation.validation.ValidateData
import com.cankolay.kapacitor.android.presentation.validation.ValidationError
import com.cankolay.kapacitor.android.presentation.validation.model.auth.SignInModel
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
        data.value = newData
    }

    suspend fun submit(): ApiResult<CreateSessionResponseDto> {
        isLoading.value = true

        val result = createSessionUseCase(
            username = data.value.username,
            password = data.value.password
        )

        isLoading.value = false
        return result
    }
}