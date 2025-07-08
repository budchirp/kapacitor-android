package com.cankolay.kapacitor.android.domain.usecase.welcome.setup

import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.TestResponse
import com.cankolay.kapacitor.android.data.remote.service.ApiTestService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TestServerConnectionUseCase
@Inject
constructor(
    private val api: ApiTestService,
    private val serverDataStore: ServerDataStore
) {
    suspend operator fun invoke(url: String, port: String): ApiResult<TestResponse> {
        return try {
            val result = withContext(Dispatchers.IO) {
                api.test(url, port.toInt())
            }

            if (result is ApiResult.Success) {
                val state = serverDataStore.flow.first()
                serverDataStore.update(state.copy(serverUrl = url, serverPort = port.toInt()))
            }

            result
        } catch (_: Exception) {
            ApiResult.Fatal(Throwable())
        }
    }
}
