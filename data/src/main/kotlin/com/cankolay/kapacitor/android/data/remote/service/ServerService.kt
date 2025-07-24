package com.cankolay.kapacitor.android.data.remote.service

import com.cankolay.kapacitor.android.data.remote.client.HttpRoutes
import com.cankolay.kapacitor.android.data.remote.client.KtorClient
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.server.GetVersionResponseDto
import com.cankolay.kapacitor.android.data.remote.model.response.server.TestResponseDto
import com.cankolay.kapacitor.android.data.remote.util.safeFetch
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ServerService
@Inject
constructor(
    private val ktorClient: KtorClient,
) {
    private var client: HttpClient

    init {
        runBlocking {
            client = ktorClient.getClient(useServerPassword = false)
        }
    }

    suspend fun test(
        url: String,
        port: Int,
    ): ApiResult<TestResponseDto> = safeFetch {
        client
            .get {
                url {
                    host = url
                    this.port = port
                    path(HttpRoutes.TEST)
                }
            }
    }

    suspend fun getVersion(
        serverPassword: String
    ): ApiResult<GetVersionResponseDto> = safeFetch {
        client
            .get {
                url {
                    path(HttpRoutes.GET_VERSION)
                }

                header("X-Server-Password", serverPassword)
            }
    }
}