package com.cankolay.kapacitor.android.data.datastore.model

data class AppState(
    val isSetupDone: Boolean,
)

fun defaultAppState(): AppState = AppState(
    isSetupDone = false,
)