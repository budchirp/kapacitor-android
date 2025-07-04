package com.cankolay.kapacitor.android.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.cankolay.kapacitor.android.data.datastore.SettingsDataStore
import com.cankolay.kapacitor.android.data.datastore.model.SettingsState
import com.cankolay.kapacitor.android.data.datastore.model.defaultSettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val settingsDataStore: SettingsDataStore,
) : ViewModel() {
    var isLoading by mutableStateOf(value = true)
        private set

    private val settingsStateFlow = settingsDataStore.flow

    @Composable
    fun getSettingsState(): State<SettingsState> =
        settingsStateFlow.collectAsStateWithLifecycle(
            initialValue =
                defaultSettingsState(),
        )

    fun updateSettingsState(state: SettingsState) {
        isLoading = true

        viewModelScope.launch {
            settingsDataStore.update(
                state = state,
            )
        }

        isLoading = false
    }

    init {
        viewModelScope.launch {
            settingsStateFlow.collect {
                isLoading = false
            }
        }
    }
}