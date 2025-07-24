package com.cankolay.kapacitor.android.data.remote.client

import android.util.Log
import com.cankolay.kapacitor.android.data.datastore.AuthDataStore
import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import javax.inject.Inject

class KtorClient
@Inject
constructor(
    private val serverDataStore: ServerDataStore,
    private val authDataStore: AuthDataStore
) {
    suspend fun getClient(useServerPassword: Boolean = true): HttpClient {
        val serverState = serverDataStore.flow.first()
        val authState =
            authDataStore.flow.first()

        val client = HttpClient(engineFactory = OkHttp) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = serverState.url
                    port = serverState.port
                }

                if (useServerPassword) {
                    header(key = "X-Server-Password", value = serverState.password)
                }

                header(key = "Authorization", value = "Bearer ${authState.token}")

                contentType(type = ContentType.Application.Json)
            }

            install(plugin = HttpTimeout) {
                requestTimeoutMillis = 10 * 1000
            }

            install(plugin = ContentNegotiation) {
                json(
                    json =
                        Json {
                            ignoreUnknownKeys = true
                            coerceInputValues = true
                            prettyPrint = true
                        },
                )
            }

            install(plugin = Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
                sanitizeHeader { header: String -> header == HttpHeaders.Authorization }
                sanitizeHeader { header: String -> header == "X-Server-Password" }
                logger =
                    object : Logger {
                        override fun log(message: String) {
                            Log.i("KtorClient", message)
                        }
                    }
            }
        }

        return client
    }
}