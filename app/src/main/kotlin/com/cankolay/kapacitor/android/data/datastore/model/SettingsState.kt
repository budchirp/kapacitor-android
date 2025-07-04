package com.cankolay.kapacitor.android.data.datastore.model

import androidx.compose.ui.graphics.Color
import com.cankolay.kapacitor.android.ui.model.Theme

data class SettingsState(
    val theme: Theme,
    val monochrome: Boolean,
    val materialYou: Boolean,
    val keyColor: Color,
    val wallpaperColors: Boolean,
)

fun defaultSettingsState(): SettingsState =
    SettingsState(
        theme = Theme.SYSTEM,
        monochrome = false,
        materialYou = false,
        keyColor = Color(color = 0xFF000000),
        wallpaperColors = false,
    )