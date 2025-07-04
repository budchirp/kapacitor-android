package com.cankolay.kapacitor.android.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.cankolay.kapacitor.android.data.datastore.model.AppState
import com.cankolay.kapacitor.android.data.datastore.model.defaultAppState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.appStateDataStore by preferencesDataStore(name = "app")

class AppDataStore @Inject
constructor(
    @ApplicationContext private val context: Context,
) {
    private val dataStore = context.appStateDataStore

    private object PreferenceKeys {
        val IS_SETUP_DONE = booleanPreferencesKey(name = "is_setup_done")
    }

    suspend fun update(state: AppState) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.IS_SETUP_DONE] =
                state.isSetupDone
        }
    }

    val flow = dataStore.data.catch { exception ->
        if (exception is IOException) {
            defaultAppState()
        } else {
            throw exception
        }
    }.map { preferences ->
        AppState(
            isSetupDone = preferences[PreferenceKeys.IS_SETUP_DONE]
                ?: defaultAppState().isSetupDone
        )
    }
}