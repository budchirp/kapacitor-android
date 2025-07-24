package com.cankolay.kapacitor.android.domain.usecase.server

import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import javax.inject.Inject

enum class ServerConnectionStatus {
    SUCCESS,
    UNREACHABLE,
    INVALID_PASSWORD
}

class TestServerConnectionUseCase
@Inject
constructor(
    private val getVersionUseCase: GetVersionUseCase,
    private val getTestUseCase: GetTestUseCase
) {
    suspend operator fun invoke(
        url: String,
        port: String,
        password: String
    ): ServerConnectionStatus? {
        val testResult = getTestUseCase(
            url = url,
            port = port
        )

        return if (testResult is ApiResult.Success) {
            val submitResult = getVersionUseCase(
                password = password
            )
            if (submitResult is ApiResult.Success) {
                ServerConnectionStatus.SUCCESS
            } else {
                ServerConnectionStatus.INVALID_PASSWORD
            }
        } else {
            ServerConnectionStatus.UNREACHABLE
        }
    }
}