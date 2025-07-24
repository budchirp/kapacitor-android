package com.cankolay.kapacitor.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cankolay.kapacitor.android.data.datastore.AppDataStore
import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.datastore.model.AppState
import com.cankolay.kapacitor.android.data.datastore.model.defaultAppState
import com.cankolay.kapacitor.android.domain.usecase.server.ServerConnectionStatus
import com.cankolay.kapacitor.android.domain.usecase.server.TestServerConnectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel
@Inject
constructor(
    private val appDataStore: AppDataStore,
    private val serverDataStore: ServerDataStore,
    private val testServerConnectionUseCase: TestServerConnectionUseCase
) : ViewModel() {
    var isLoading = MutableStateFlow(value = true)
        private set

    var isDrawerEnabled = MutableStateFlow(value = false)
        private set

    var status = MutableStateFlow<ServerConnectionStatus?>(value = null)
        private set

    val appDataStoreFlow = appDataStore.flow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = defaultAppState()
    )

    fun updateAppDataStore(app: AppState) {
        viewModelScope.launch {
            appDataStore.update(
                state = app,
            )
        }
    }

    fun setIsDrawerEnabled(enable: Boolean) {
        isDrawerEnabled.value = enable
    }

    suspend fun fetchServer() {
        val appState = appDataStore.flow.first()
        if (appState.isSetupDone) {
            val serverState = serverDataStore.flow.first()

            status.value = testServerConnectionUseCase(
                url = serverState.url,
                port = serverState.port.toString(),
                password = serverState.password
            )
        }
    }

    init {
        viewModelScope.launch {
            fetchServer()

            if (status.value == ServerConnectionStatus.SUCCESS) {
                setIsDrawerEnabled(enable = true)
            }

            isLoading.value = false
        }
    }
}