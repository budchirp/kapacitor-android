package com.cankolay.kapacitor.android.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cankolay.kapacitor.android.data.datastore.model.ServerState
import com.cankolay.kapacitor.android.data.datastore.model.defaultServerState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.serverDataStore by preferencesDataStore(name = "server")

class ServerDataStore
@Inject
constructor(
    @ApplicationContext private val context: Context,
) {
    private val dataStore = context.serverDataStore

    private object PreferenceKeys {
        val SERVER_URL =
            stringPreferencesKey(name = "server_url")
        val SERVER_PORT =
            intPreferencesKey(name = "server_port")
        val SERVER_PASSWORD =
            stringPreferencesKey(name = "server_password")
    }

    suspend fun update(state: ServerState) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.SERVER_URL] =
                state.serverUrl
            preferences[PreferenceKeys.SERVER_PORT] =
                state.serverPort
            preferences[PreferenceKeys.SERVER_PASSWORD] =
                state.serverPassword
        }
    }

    val flow =
        dataStore
            .data
            .catch { exception ->
                if (exception is IOException) {
                    defaultServerState()
                } else {
                    throw exception
                }
            }.map { preferences ->
                ServerState(
                    serverUrl = preferences[PreferenceKeys.SERVER_URL]
                        ?: defaultServerState().serverUrl,
                    serverPort = preferences[PreferenceKeys.SERVER_PORT]
                        ?: defaultServerState().serverPort,
                    serverPassword = preferences[PreferenceKeys.SERVER_PASSWORD]
                        ?: defaultServerState().serverPassword,
                )
            }
}