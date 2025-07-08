package com.cankolay.kapacitor.android.domain.usecase.auth

import com.cankolay.kapacitor.android.common.validation.model.auth.CreateUserModel
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.CreateUserResponse
import com.cankolay.kapacitor.android.data.remote.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateUserUseCase
@Inject
constructor(
    private val userService: UserService
) {
    suspend operator fun invoke(data: CreateUserModel): ApiResult<CreateUserResponse> {
        return try {
            val result = withContext(Dispatchers.IO) {
                userService.create(data = data)
            }

            result
        } catch (_: Exception) {
            ApiResult.Fatal(Throwable())
        }
    }
}
