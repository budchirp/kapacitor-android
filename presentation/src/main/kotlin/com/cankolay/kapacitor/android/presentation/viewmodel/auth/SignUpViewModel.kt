package com.cankolay.kapacitor.android.presentation.viewmodel.auth

import androidx.lifecycle.ViewModel
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.user.CreateUserResponseDto
import com.cankolay.kapacitor.android.domain.usecase.auth.CreateUserUseCase
import com.cankolay.kapacitor.android.presentation.validation.ValidateData
import com.cankolay.kapacitor.android.presentation.validation.ValidationError
import com.cankolay.kapacitor.android.presentation.validation.model.auth.SignUpModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) :
    ViewModel() {
    var isLoading = MutableStateFlow(value = false)
        private set

    var data = MutableStateFlow(value = SignUpModel())
        private set

    var errors = MutableStateFlow<Map<String, Set<ValidationError>>>(value = emptyMap())
        private set

    fun validateData(validationData: SignUpModel = data.value): Map<String, Set<ValidationError>> {
        errors.value =
            ValidateData(model = SignUpModel::class).validate(validationData)

        return errors.value
    }

    fun updateData(newData: SignUpModel) {
        data.value = newData
    }

    suspend fun submit(): ApiResult<CreateUserResponseDto> {
        isLoading.value = true

        val result = createUserUseCase(
            username = data.value.username,
            password = data.value.password,
        )

        isLoading.value = false
        return result
    }
}