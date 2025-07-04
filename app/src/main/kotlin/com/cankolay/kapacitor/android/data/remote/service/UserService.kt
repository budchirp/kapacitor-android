package com.cankolay.kapacitor.android.data.remote.service

import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking
import com.cankolay.kapacitor.android.data.remote.client.KtorClient
import javax.inject.Inject

class UserService
@Inject
constructor(
    ktorClient: KtorClient,
) {
    private var client: HttpClient

    init {
        runBlocking {
            client = ktorClient.getClient()
        }
    }
}