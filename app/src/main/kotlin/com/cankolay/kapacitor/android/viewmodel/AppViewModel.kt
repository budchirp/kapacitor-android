package com.cankolay.kapacitor.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cankolay.kapacitor.android.data.datastore.AppDataStore
import com.cankolay.kapacitor.android.data.datastore.model.AppState
import com.cankolay.kapacitor.android.data.datastore.model.defaultAppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel
@Inject
constructor(
    private val appDataStore: AppDataStore
) : ViewModel() {
    var isLoading = MutableStateFlow(value = true)
        private set

    var isDrawerEnabled = MutableStateFlow(value = false)
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

    init {
        viewModelScope.launch {
            appDataStoreFlow.collect { state ->
                isLoading.value = false

                if (state.isSetupDone) {
                    setIsDrawerEnabled(enable = true)
                }
            }
        }
    }
}