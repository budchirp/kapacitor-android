package com.cankolay.kapacitor.android.data.datastore.model

data class ServerState(
    val url: String,
    val port: Int,
    val password: String,
)

fun defaultServerState(): ServerState =
    ServerState(
        url = "",
        port = 8080,
        password = "",
    )