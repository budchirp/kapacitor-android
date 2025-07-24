package com.cankolay.kapacitor.android.presentation.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cankolay.kapacitor.android.data.datastore.ServerDataStore
import com.cankolay.kapacitor.android.data.datastore.model.ServerState
import com.cankolay.kapacitor.android.data.datastore.model.defaultServerState
import com.cankolay.kapacitor.android.presentation.validation.ValidateData
import com.cankolay.kapacitor.android.presentation.validation.ValidationError
import com.cankolay.kapacitor.android.presentation.validation.model.settings.ServerStateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerSettingsViewModel
@Inject
constructor(
    private val serverDataStore: ServerDataStore,
) : ViewModel() {
    var isLoading = MutableStateFlow(value = false)
        private set

    val serverStateFlow = serverDataStore.flow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = defaultServerState(),
    )

    var data = MutableStateFlow(
        value = ServerStateModel(
            url = serverStateFlow.value.url,
            port = serverStateFlow.value.port.toString(),
            password = serverStateFlow.value.password
        )
    )
        private set

    var errors = MutableStateFlow<Map<String, Set<ValidationError>>>(value = emptyMap())
        private set

    fun validateData(validationData: ServerStateModel = data.value): Map<String, Set<ValidationError>> {
        errors.value =
            ValidateData(model = ServerStateModel::class).validate(validationData)

        return errors.value
    }

    fun updateData(newData: ServerStateModel) {
        data.value = newData
    }

    suspend fun submit() {
        isLoading.value = true

        serverDataStore.update(
            state = ServerState(
                url = data.value.url,
                port = data.value.port.toInt(),
                password = data.value.password
            )
        )

        isLoading.value = false
    }

    init {
        viewModelScope.launch {
            val state = serverDataStore.flow.first()
            data.value = ServerStateModel(
                url = state.url,
                port = state.port.toString(),
                password = state.password
            )
        }
    }
}