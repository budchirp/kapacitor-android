package com.cankolay.kapacitor.android.presentation.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cankolay.kapacitor.android.data.datastore.SettingsDataStore
import com.cankolay.kapacitor.android.data.datastore.model.SettingsState
import com.cankolay.kapacitor.android.data.datastore.model.defaultSettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val settingsDataStore: SettingsDataStore,
) : ViewModel() {
    val settingsStateFlow = settingsDataStore.flow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = defaultSettingsState(),
    )

    fun updateSettingsState(state: SettingsState) {
        viewModelScope.launch {
            settingsDataStore.update(
                state = state,
            )
        }
    }
}