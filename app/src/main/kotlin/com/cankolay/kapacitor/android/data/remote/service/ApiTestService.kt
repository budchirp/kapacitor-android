package com.cankolay.kapacitor.android.data.remote.service

import com.cankolay.kapacitor.android.data.remote.client.HttpRoutes
import com.cankolay.kapacitor.android.data.remote.client.KtorClient
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.GetVersionEndpointResponse
import com.cankolay.kapacitor.android.data.remote.model.response.TestEndpointResponse
import com.cankolay.kapacitor.android.data.remote.util.FetchUtil
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ApiTestService
@Inject
constructor(
    private val fetchUtil: FetchUtil,
    private val ktorClient: KtorClient,
) {
    private var client: HttpClient

    init {
        runBlocking {
            client = ktorClient.getClient()
        }
    }

    suspend fun test(
        serverUrl: String,
        serverPort: Int,
    ): ApiResult<TestEndpointResponse> {
        return fetchUtil.safeFetch {
            val body =
                client
                    .get {
                        url {
                            host = serverUrl
                            port = serverPort
                            path(HttpRoutes.TEST)
                        }
                    }.body<TestEndpointResponse>()

            return@safeFetch ApiResult.Success(data = body)
        }
    }

    suspend fun getVersion(
        serverPassword: String
    ): ApiResult<GetVersionEndpointResponse> {
        return fetchUtil.safeFetch {
            val body =
                client
                    .get {
                        url {
                            path(HttpRoutes.GET_VERSION)
                        }

                        header("X-Server-Password", serverPassword)
                    }.body<GetVersionEndpointResponse>()

            return@safeFetch ApiResult.Success(data = body)
        }
    }
}