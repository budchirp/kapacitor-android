package com.cankolay.kapacitor.android.data.remote.service.user

import com.cankolay.kapacitor.android.data.remote.client.HttpRoutes
import com.cankolay.kapacitor.android.data.remote.client.KtorClient
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.request.user.CreateUserRequestDto
import com.cankolay.kapacitor.android.data.remote.model.response.user.CreateUserResponseDto
import com.cankolay.kapacitor.android.data.remote.util.safeFetch
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UserService
@Inject
constructor(
    private val ktorClient: KtorClient,
) {
    private var client: HttpClient

    init {
        runBlocking {
            client = ktorClient.getClient()
        }
    }

    suspend fun create(
        data: CreateUserRequestDto
    ): ApiResult<CreateUserResponseDto> = safeFetch {
        client
            .post {
                setBody(body = data)

                url {
                    path(HttpRoutes.CREATE_USER)
                }
            }
    }
}