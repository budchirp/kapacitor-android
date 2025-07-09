package com.cankolay.kapacitor.android.data.remote.service.user

import com.cankolay.kapacitor.android.data.remote.client.HttpRoutes
import com.cankolay.kapacitor.android.data.remote.client.KtorClient
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.user.CreateUserResponse
import com.cankolay.kapacitor.android.data.remote.util.FetchUtil
import com.cankolay.kapacitor.android.ui.validation.model.auth.SignUpModel
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
    private val fetchUtil: FetchUtil,
) {
    private var client: HttpClient

    init {
        runBlocking {
            client = ktorClient.getClient()
        }
    }

    suspend fun create(
        data: SignUpModel
    ): ApiResult<CreateUserResponse> = fetchUtil.safeFetch {
        client
            .post {
                setBody(body = data)

                url {
                    path(HttpRoutes.CREATE_USER)
                }
            }
    }
}