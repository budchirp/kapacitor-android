package com.cankolay.kapacitor.android.data.remote.model

import com.cankolay.kapacitor.android.data.remote.error.APIErrorReason

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()

    data class Error(val message: String, val reason: APIErrorReason) : ApiResult<Nothing>()

    data class Fatal(val throwable: Throwable) : ApiResult<Nothing>()
}