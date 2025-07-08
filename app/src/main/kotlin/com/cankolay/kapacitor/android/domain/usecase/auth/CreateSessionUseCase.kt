package com.cankolay.kapacitor.android.domain.usecase.auth

import com.cankolay.kapacitor.android.data.datastore.AppDataStore
import com.cankolay.kapacitor.android.data.datastore.AuthDataStore
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.session.CreateSessionResponse
import com.cankolay.kapacitor.android.data.remote.service.SessionService
import com.cankolay.kapacitor.android.ui.validation.model.auth.SignInModel
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
    suspend operator fun invoke(data: SignInModel): ApiResult<CreateSessionResponse> {
        return try {
            val result = withContext(context = Dispatchers.IO) {
                sessionService.create(data = data)
            }

            if (result is ApiResult.Success) {
                val authState = authDataStore.flow.first()
                authDataStore.update(state = authState.copy(token = result.response.data.token))

                val appState = appDataStore.flow.first()
                appDataStore.update(state = appState.copy(isSetupDone = true))
            }

            result
        } catch (_: Exception) {
            ApiResult.Fatal(Throwable())
        }
    }
}
