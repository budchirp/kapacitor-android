package com.cankolay.kapacitor.android.data.datastore.model

data class AuthState(
    val token: String
)

fun defaultAuthState(): AuthState = AuthState(
    token = ""
)