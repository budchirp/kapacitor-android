package com.cankolay.kapacitor.android.data.datastore.model

enum class Theme(
    val type: String,
) {
    SYSTEM("system"),
    DARK("dark"),
    LIGHT("light"),
}

data class SettingsState(
    val theme: Theme,
    val monochrome: Boolean,
    val materialYou: Boolean,
    val keyColor: String,
    val wallpaperColors: Boolean,
)

fun defaultSettingsState(): SettingsState =
    SettingsState(
        theme = Theme.SYSTEM,
        monochrome = false,
        materialYou = false,
        keyColor = "#000000",
        wallpaperColors = false,
    )