package com.cankolay.kapacitor.android.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cankolay.kapacitor.android.data.datastore.model.AuthState
import com.cankolay.kapacitor.android.data.datastore.model.defaultAuthState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.authDataStore by preferencesDataStore(name = "auth")

class AuthDataStore @Inject
constructor(
    @ApplicationContext private val context: Context,
) {
    private val dataStore = context.authDataStore

    private object PreferenceKeys {
        val TOKEN: Preferences.Key<String> = stringPreferencesKey(name = "token")
    }

    suspend fun update(state: AuthState) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.TOKEN] =
                state.token
        }

    }

    val flow = dataStore.data.catch { exception ->
        if (exception is IOException) {
            defaultAuthState()
        } else {
            throw exception
        }
    }.map { preferences ->
        AuthState(
            token = preferences[PreferenceKeys.TOKEN] ?: defaultAuthState().token,
        )
    }
}