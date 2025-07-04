package com.cankolay.kapacitor.android.data.remote.model

interface ApiResponse<T> {
    val message: String
    val body: T
}