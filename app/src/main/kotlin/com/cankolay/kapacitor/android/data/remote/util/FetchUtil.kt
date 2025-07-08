package com.cankolay.kapacitor.android.data.remote.util

import com.cankolay.kapacitor.android.data.remote.model.APIErrorReason
import com.cankolay.kapacitor.android.data.remote.model.ApiErrorResponse
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class FetchUtil
@Inject
constructor() {
    suspend inline fun <reified T : Any> safeFetch(block: suspend () -> HttpResponse): ApiResult<T> {
        return try {
            val response = block()

            val statusCode = response.status.value
            if (statusCode in 200..299) {
                val body = response.body<T>()
                ApiResult.Success(
                    response = body
                )
            } else {
                val body = response.body<ApiErrorResponse>()
                when (statusCode) {
                    in 300..399 -> {
                        ApiResult.Error(
                            message = body.message,
                            reason = APIErrorReason.SERVER
                        )
                    }

                    in 400..499 -> {
                        ApiResult.Error(message = body.message, reason = APIErrorReason.USER)
                    }

                    in 500..599 -> {
                        ApiResult.Error(
                            message = body.message,
                            reason = APIErrorReason.SERVER
                        )
                    }

                    else -> {
                        ApiResult.Error(
                            message = body.message,
                            reason = APIErrorReason.SERVER
                        )
                    }
                }
            }
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
}