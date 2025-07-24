package com.cankolay.kapacitor.android.data.remote.service.user.profile

import com.cankolay.kapacitor.android.data.remote.client.HttpRoutes
import com.cankolay.kapacitor.android.data.remote.client.KtorClient
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.user.profile.GetProfileResponseDto
import com.cankolay.kapacitor.android.data.remote.util.safeFetch
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ProfileService
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

    suspend fun get(): ApiResult<GetProfileResponseDto> = safeFetch {
        client
            .get {
                url {
                    path(HttpRoutes.GET_PROFILE)
                }
            }
    }
}