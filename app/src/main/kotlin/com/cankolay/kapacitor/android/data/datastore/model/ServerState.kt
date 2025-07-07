package com.cankolay.kapacitor.android.data.datastore.model

data class ServerState(
    val serverUrl: String,
    val serverPort: Int,
    val serverPassword: String,
)

fun defaultServerState(): ServerState =
    ServerState(
        serverUrl = "",
        serverPort = 8080,
        serverPassword = "",
    )