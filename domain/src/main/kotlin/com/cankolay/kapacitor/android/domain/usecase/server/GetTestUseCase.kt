package com.cankolay.kapacitor.android.domain.usecase.server

import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.server.TestResponseDto
import com.cankolay.kapacitor.android.data.remote.service.ServerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTestUseCase
@Inject
constructor(
    private val serverService: ServerService,
    private val serverDataStore: ServerDataStore
) {
    suspend operator fun invoke(url: String, port: String): ApiResult<TestResponseDto> = try {
        val result = withContext(context = Dispatchers.IO) {
            serverService.test(url = url, port = port.toInt())
        }

        if (result is ApiResult.Success) {
            val state = serverDataStore.flow.first()
            serverDataStore.update(
                state = state.copy(
                    url = url,
                    port = port.toInt()
                )
            )
        }

        result
    } catch (e: Exception) {
        ApiResult.Fatal(exception = e)
    }
}