package com.cankolay.kapacitor.android.domain.usecase.auth

import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.request.user.CreateUserRequestDto
import com.cankolay.kapacitor.android.data.remote.model.response.user.CreateUserResponseDto
import com.cankolay.kapacitor.android.data.remote.service.user.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateUserUseCase
@Inject
constructor(
    private val userService: UserService
) {
    suspend operator fun invoke(
        username: String,
        password: String
    ): ApiResult<CreateUserResponseDto> = try {
        withContext(context = Dispatchers.IO) {
            userService.create(
                data = CreateUserRequestDto(
                    username = username,
                    password = password
                )
            )
        }
    } catch (e: Exception) {
        ApiResult.Fatal(exception = e)
    }
}