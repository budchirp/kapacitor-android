package com.cankolay.kapacitor.android.data.remote.model.response

interface ApiResponse<T> {
    val message: String
    val messageCode: String
    val data: T
}