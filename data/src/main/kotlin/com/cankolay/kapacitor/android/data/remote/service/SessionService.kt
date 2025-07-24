package com.cankolay.kapacitor.android.data.remote.service

import com.cankolay.kapacitor.android.data.remote.client.HttpRoutes
import com.cankolay.kapacitor.android.data.remote.client.KtorClient
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.request.session.CreateSessionRequestDto
import com.cankolay.kapacitor.android.data.remote.model.response.session.CreateSessionResponseDto
import com.cankolay.kapacitor.android.data.remote.util.safeFetch
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SessionService
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
        data: CreateSessionRequestDto
    ): ApiResult<CreateSessionResponseDto> = safeFetch {
        client
            .post {
                setBody(body = data)

                url {
                    path(HttpRoutes.CREATE_SESSION)
                }
            }
    }
}