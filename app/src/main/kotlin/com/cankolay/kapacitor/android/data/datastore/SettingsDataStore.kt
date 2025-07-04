package com.cankolay.kapacitor.android.data.datastore

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cankolay.kapacitor.android.data.datastore.model.SettingsState
import com.cankolay.kapacitor.android.data.datastore.model.defaultSettingsState
import com.cankolay.kapacitor.android.ui.model.Theme
import com.cankolay.kapacitor.android.ui.utils.fromColor
import com.cankolay.kapacitor.android.ui.utils.fromHex
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.settingsDataStore by preferencesDataStore(name = "settings")


class SettingsDataStore
@Inject
constructor(
    @ApplicationContext private val context: Context,
) {
    private val dataStore = context.settingsDataStore

    private object PreferenceKeys {

        val THEME = stringPreferencesKey(name = "theme")
        val MONOCHROME =
            booleanPreferencesKey(name = "black_theme")
        val MATERIAL_YOU =
            booleanPreferencesKey(name = "material_you")
        val KEY_COLOR =
            stringPreferencesKey(name = "key_color")
        val WALLPAPER_COLORS =
            booleanPreferencesKey(name = "wallpaper_colors")
    }

    suspend fun update(state: SettingsState) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.THEME] =
                state.theme.name
            preferences[PreferenceKeys.MONOCHROME] =
                state.monochrome
            preferences[PreferenceKeys.MATERIAL_YOU] =
                state.materialYou
            preferences[PreferenceKeys.KEY_COLOR] =
                Color.fromColor(color = state.keyColor)
            preferences[PreferenceKeys.WALLPAPER_COLORS] =
                state.wallpaperColors
        }
    }

    val flow =
        dataStore
            .data
            .catch { exception ->
                if (exception is IOException) {
                    defaultSettingsState()
                } else {
                    throw exception
                }
            }.map { preferences ->
                SettingsState(
                    theme = preferences[PreferenceKeys.THEME]?.let { type: String ->
                        Theme.valueOf(value = type)
                    }
                        ?: defaultSettingsState().theme,
                    monochrome = preferences[PreferenceKeys.MONOCHROME]
                        ?: defaultSettingsState().monochrome,
                    materialYou = preferences[PreferenceKeys.MATERIAL_YOU]
                        ?: defaultSettingsState().materialYou,
                    keyColor = Color.fromHex(
                        hexColor = preferences[PreferenceKeys.KEY_COLOR]
                            ?: Color.fromColor(defaultSettingsState().keyColor)
                    ),
                    wallpaperColors = preferences[PreferenceKeys.WALLPAPER_COLORS]
                        ?: defaultSettingsState().wallpaperColors,
                )
            }
}