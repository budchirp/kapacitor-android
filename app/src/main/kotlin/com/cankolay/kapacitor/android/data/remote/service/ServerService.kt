package com.cankolay.kapacitor.android.data.remote.service

import com.cankolay.kapacitor.android.data.remote.client.HttpRoutes
import com.cankolay.kapacitor.android.data.remote.client.KtorClient
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.server.GetVersionResponse
import com.cankolay.kapacitor.android.data.remote.model.response.server.TestResponse
import com.cankolay.kapacitor.android.data.remote.util.FetchUtil
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ServerService
@Inject
constructor(
    private val fetchUtil: FetchUtil,
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
    ): ApiResult<TestResponse> = fetchUtil.safeFetch {
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
    ): ApiResult<GetVersionResponse> = fetchUtil.safeFetch {
        client
            .get {
                url {
                    path(HttpRoutes.GET_VERSION)
                }

                header("X-Server-Password", serverPassword)
            }
    }
}