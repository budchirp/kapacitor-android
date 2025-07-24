package com.cankolay.kapacitor.android.domain.usecase.server

import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.server.GetVersionResponseDto
import com.cankolay.kapacitor.android.data.remote.service.ServerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetVersionUseCase
@Inject
constructor(
    private val serverService: ServerService,
    private val serverDataStore: ServerDataStore
) {
    suspend operator fun invoke(password: String): ApiResult<GetVersionResponseDto> = try {
        val result = withContext(context = Dispatchers.IO) {
            serverService.getVersion(serverPassword = password)
        }

        if (result is ApiResult.Success) {
            val serverState = serverDataStore.flow.first()
            serverDataStore.update(state = serverState.copy(password = password))
        }

        result
    } catch (e: Exception) {
        ApiResult.Fatal(exception = e)
    }
}