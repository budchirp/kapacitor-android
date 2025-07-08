package com.cankolay.kapacitor.android.domain.usecase.welcome.setup

import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.server.TestResponse
import com.cankolay.kapacitor.android.data.remote.service.ServerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TestServerConnectionUseCase
@Inject
constructor(
    private val api: ServerService,
    private val serverDataStore: ServerDataStore
) {
    suspend operator fun invoke(url: String, port: String): ApiResult<TestResponse> {
        return try {
            val result = withContext(context = Dispatchers.IO) {
                api.test(url = url, port = port.toInt())
            }

            if (result is ApiResult.Success) {
                val state = serverDataStore.flow.first()
                serverDataStore.update(
                    state = state.copy(
                        serverUrl = url,
                        serverPort = port.toInt()
                    )
                )
            }

            result
        } catch (_: Exception) {
            ApiResult.Fatal(Throwable())
        }
    }
}
