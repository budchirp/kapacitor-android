package com.cankolay.kapacitor.android.domain.usecase.auth

import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.user.CreateUserResponse
import com.cankolay.kapacitor.android.data.remote.service.user.UserService
import com.cankolay.kapacitor.android.ui.validation.model.auth.SignUpModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateUserUseCase
@Inject
constructor(
    private val userService: UserService
) {
    suspend operator fun invoke(data: SignUpModel): ApiResult<CreateUserResponse> {
        return try {
            val result = withContext(context = Dispatchers.IO) {
                userService.create(data = data)
            }

            result
        } catch (_: Exception) {
            ApiResult.Fatal(Throwable())
        }
    }
}
