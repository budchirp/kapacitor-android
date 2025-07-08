package com.cankolay.kapacitor.android.data.remote.service

import com.cankolay.kapacitor.android.common.validation.model.auth.CreateUserModel
import com.cankolay.kapacitor.android.data.remote.client.HttpRoutes
import com.cankolay.kapacitor.android.data.remote.client.KtorClient
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.CreateUserResponse
import com.cankolay.kapacitor.android.data.remote.util.FetchUtil
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UserService
@Inject
constructor(
    private val ktorClient: KtorClient,
    private val fetchUtil: FetchUtil,
) {
    private var client: HttpClient

    init {
        runBlocking {
            client = ktorClient.getClient()
        }
    }

    suspend fun create(
        data: CreateUserModel
    ): ApiResult<CreateUserResponse> {
        return fetchUtil.safeFetch {
            val body =
                client
                    .post {
                        setBody(body = data)

                        url {
                            path(HttpRoutes.CREATE_USER)
                        }
                    }.body<CreateUserResponse>()

            ApiResult.Success(data = body)
        }
    }
}