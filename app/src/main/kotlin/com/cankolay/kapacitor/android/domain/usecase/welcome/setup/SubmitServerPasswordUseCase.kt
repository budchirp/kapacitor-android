package com.cankolay.kapacitor.android.domain.usecase.welcome.setup

import com.cankolay.kapacitor.android.data.datastore.AppDataStore
import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.server.GetVersionResponse
import com.cankolay.kapacitor.android.data.remote.service.ServerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SubmitServerPasswordUseCase
@Inject
constructor(
    private val serverService: ServerService,
    private val serverDataStore: ServerDataStore,
    private val appDataStore: AppDataStore
) {
    suspend operator fun invoke(password: String): ApiResult<GetVersionResponse> {
        return try {
            val result = withContext(context = Dispatchers.IO) {
                serverService.getVersion(serverPassword = password)
            }

            if (result is ApiResult.Success) {
                val serverState = serverDataStore.flow.first()
                serverDataStore.update(state = serverState.copy(serverPassword = password))
            }

            result
        } catch (_: Exception) {
            ApiResult.Fatal(Throwable())
        }
    }
}
