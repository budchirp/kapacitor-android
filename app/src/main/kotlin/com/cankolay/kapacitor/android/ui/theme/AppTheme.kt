package com.cankolay.kapacitor.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.cankolay.kapacitor.android.ui.model.PaletteStyle.Monochrome
import com.cankolay.kapacitor.android.ui.model.PaletteStyle.TonalSpot
import com.cankolay.kapacitor.android.ui.util.UiUtil
import com.cankolay.kapacitor.android.viewmodel.settings.SettingsViewModel

@Composable
fun AppTheme(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val settingsState by settingsViewModel.settingsStateFlow.collectAsState()

    val isDark = UiUtil.isDark(settingsState = settingsState)

    val colorScheme = dynamicColorScheme(
        keyColor = if (settingsState.materialYou) if (settingsState.wallpaperColors) colorResource(
            id = android.R.color.system_accent1_500
        ) else settingsState.keyColor else accentColor,
        style = if (settingsState.monochrome) Monochrome else TonalSpot,
        isDark = isDark
    ).run {
        if (isDark && settingsState.monochrome) copy(
            surface = Color.Black,
            background = Color.Black,
            surfaceContainerLow = Color.Black,
            surfaceContainer = surfaceContainerLow,
            surfaceContainerHigh = surfaceContainerLow,
            surfaceContainerHighest = surfaceContainer
        ) else this
    }

    val view = LocalView.current

    val window = view.context.findWindow()
    window?.let {
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !isDark
    }

    MaterialTheme(
        colorScheme = colorScheme, content = content
    )
}