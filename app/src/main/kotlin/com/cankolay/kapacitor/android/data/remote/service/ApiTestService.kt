package com.cankolay.kapacitor.android.data.remote.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import com.cankolay.kapacitor.android.data.remote.client.HttpRoutes
import com.cankolay.kapacitor.android.data.remote.client.KtorClient
import com.cankolay.kapacitor.android.data.remote.error.APIErrorReason
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.GetVersionEndpointResponse
import com.cankolay.kapacitor.android.data.remote.model.response.TestEndpointResponse
import javax.inject.Inject

class ApiTestService
@Inject
constructor(
    ktorClient: KtorClient,
) {
    private var client: HttpClient

    suspend fun test(
        serverUrl: String,
        serverPort: Int,
    ): ApiResult<TestEndpointResponse> {
        try {
            val body: TestEndpointResponse =
                client
                    .get {
                        url {
                            host = serverUrl
                            port = serverPort
                            path(HttpRoutes.TEST)
                        }
                    }.body<TestEndpointResponse>()

            return ApiResult.Success(data = body)
        } catch (e: RedirectResponseException) {
            return ApiResult.Error(message = e.message, reason = APIErrorReason.SERVER)
        } catch (e: ClientRequestException) {
            return ApiResult.Error(message = e.message, reason = APIErrorReason.USER)
        } catch (e: ServerResponseException) {
            return ApiResult.Error(message = e.message, reason = APIErrorReason.SERVER)
        } catch (e: Exception) {
            return ApiResult.Fatal(throwable = e)
        }
    }

    suspend fun getVersion(
        serverPassword: String
    ): ApiResult<GetVersionEndpointResponse> {
        try {
            val body: GetVersionEndpointResponse =
                client
                    .get {
                        url {
                            path(HttpRoutes.GET_VERSION)
                        }

                        header("X-Server-Password", serverPassword)
                    }.body<GetVersionEndpointResponse>()

            return ApiResult.Success(data = body)
        } catch (e: RedirectResponseException) {
            return ApiResult.Error(message = e.message, reason = APIErrorReason.SERVER)
        } catch (e: ClientRequestException) {
            return ApiResult.Error(message = e.message, reason = APIErrorReason.USER)
        } catch (e: ServerResponseException) {
            return ApiResult.Error(message = e.message, reason = APIErrorReason.SERVER)
        } catch (e: Exception) {
            return ApiResult.Fatal(throwable = e)
        }
    }

    init {
        runBlocking {
            client = ktorClient.getClient()
        }
    }
}