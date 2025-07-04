package com.cankolay.kapacitor.android.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.cankolay.kapacitor.android.data.datastore.AppDataStore
import com.cankolay.kapacitor.android.data.datastore.model.AppState
import com.cankolay.kapacitor.android.data.datastore.model.defaultAppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel
@Inject
constructor(
    private val appDataStore: AppDataStore
) : ViewModel() {
    var isLoading by mutableStateOf(value = true)
        private set

    var isDrawerEnabled by mutableStateOf(value = true)
        private set

    val appDataStoreFlow = appDataStore.flow

    @Composable
    fun getAppDataStore(): State<AppState> =
        appDataStoreFlow.collectAsStateWithLifecycle(initialValue = defaultAppState())

    fun updateAppDataStore(app: AppState) {
        isLoading = true

        viewModelScope.launch {
            appDataStore.update(
                state = app,
            )

        }

        isLoading = false
    }

    fun setIsDrawerEnabled(enable: Boolean) {
        isDrawerEnabled = enable
    }

    init {
        viewModelScope.launch {
            appDataStoreFlow.collect { appState: AppState ->
                isLoading = false

                if (!appState.isSetupDone) {
                    setIsDrawerEnabled(enable = false)
                }
            }
        }
    }
}