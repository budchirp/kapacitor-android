package com.cankolay.kapacitor.android.data.remote.model

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(
        val response: T
    ) :
        ApiResult<T>()

    data class Error(val message: String, val reason: APIErrorReason) : ApiResult<Nothing>()

    data class Fatal(val exception: Throwable) : ApiResult<Nothing>()
}