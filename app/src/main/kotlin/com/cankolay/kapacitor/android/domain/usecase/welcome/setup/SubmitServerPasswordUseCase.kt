package com.cankolay.kapacitor.android.domain.usecase.welcome.setup

import com.cankolay.kapacitor.android.data.datastore.AppDataStore
import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.GetVersionResponse
import com.cankolay.kapacitor.android.data.remote.service.ApiTestService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SubmitServerPasswordUseCase
@Inject
constructor(
    private val apiTestService: ApiTestService,
    private val serverDataStore: ServerDataStore,
    private val appDataStore: AppDataStore
) {
    suspend operator fun invoke(password: String): ApiResult<GetVersionResponse> {
        return try {
            val result = withContext(Dispatchers.IO) {
                apiTestService.getVersion(serverPassword = password)
            }

            if (result is ApiResult.Success) {
                val serverState = serverDataStore.flow.first()
                serverDataStore.update(serverState.copy(serverPassword = password))

                val appState = appDataStore.flow.first()
                appDataStore.update(appState.copy(isSetupDone = true))
            }

            result
        } catch (_: Exception) {
            ApiResult.Fatal(Throwable())
        }
    }
}
