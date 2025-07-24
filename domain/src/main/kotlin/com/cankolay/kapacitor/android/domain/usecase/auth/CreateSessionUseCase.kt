package com.cankolay.kapacitor.android.domain.usecase.auth

import com.cankolay.kapacitor.android.data.datastore.AppDataStore
import com.cankolay.kapacitor.android.data.datastore.AuthDataStore
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.request.session.CreateSessionRequestDto
import com.cankolay.kapacitor.android.data.remote.model.response.session.CreateSessionResponseDto
import com.cankolay.kapacitor.android.data.remote.service.SessionService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateSessionUseCase
@Inject
constructor(
    private val sessionService: SessionService,
    private val authDataStore: AuthDataStore,
    private val appDataStore: AppDataStore
) {
    suspend operator fun invoke(
        username: String,
        password: String
    ): ApiResult<CreateSessionResponseDto> = try {
        val result = withContext(context = Dispatchers.IO) {
            sessionService.create(
                data = CreateSessionRequestDto(
                    username = username,
                    password = password
                )
            )
        }

        if (result is ApiResult.Success) {
            val authState = authDataStore.flow.first()
            authDataStore.update(state = authState.copy(token = result.response.data.token))

            val appState = appDataStore.flow.first()
            appDataStore.update(state = appState.copy(isSetupDone = true))
        }

        result
    } catch (e: Exception) {
        ApiResult.Fatal(exception = e)
    }
}