package com.cankolay.kapacitor.android.data.remote.util

import com.cankolay.kapacitor.android.data.remote.model.APIErrorReason
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import javax.inject.Inject

class FetchUtil
@Inject
constructor() {
    suspend fun <T : Any> safeFetch(block: suspend () -> ApiResult<T>): ApiResult<T> {
        return try {
            block()
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