package com.cankolay.kapacitor.android.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.cankolay.kapacitor.android.R

sealed class Route(
    val destination: String,
    val title: Int,
    val description: Int = R.string.empty,
    val icon: ImageVector = Icons.Filled.Home,
    val unselectedIcon: ImageVector = Icons.Outlined.Home,
) {
    data object Welcome :
        Route(
            destination = "welcome",
            title = R.string.welcome,
        )

    data object ServerDetails :
        Route(
            destination = "server_details",
            title = R.string.server_details,
        )

    data object ServerPassword :
        Route(
            destination = "server_password",
            title = R.string.server_password,
        )

    data object Home :
        Route(
            destination = "home",
            title = R.string.home,
            icon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        )

    data object Settings :
        Route(
            destination = "settings",
            title = R.string.settings,
            icon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        )

    data object GeneralSettings :
        Route(
            destination = "settings/general",
            title = R.string.general_settings,
            description = R.string.general_settings_desc,
            icon = Icons.Filled.Settings,
        )

    data object Appearance :
        Route(
            destination = "settings/appearance",
            title = R.string.appearance,
            description = R.string.appearance_desc,
            icon = Icons.Filled.Palette,
        )

    data object Theme :
        Route(
            destination = "settings/appearance/theme",
            title = R.string.appearance_theme,
            description = R.string.appearance_theme_desc,
            icon = Icons.Filled.DarkMode,
        )

    data object MaterialYou :
        Route(
            destination = "settings/appearance/material-you",
            title = R.string.appearance_material_you,
            description = R.string.appearance_material_you_desc,
            icon = Icons.Filled.Colorize,
        )

    data object Languages :
        Route(
            destination = "settings/languages",
            title = R.string.languages,
            description = R.string.languages_desc,
            icon = Icons.Filled.Translate,
        )

    data object About :
        Route(
            destination = "settings/about",
            title = R.string.about,
            description = R.string.about_desc,
            icon = Icons.Filled.Info,
        )

    data object Licenses :
        Route(
            destination = "settings/about/licenses",
            title = R.string.licenses,
            description = R.string.licenses_desc,
            icon = Icons.Filled.Gavel,
        )
}

val drawerRoutes = listOf(Route.Home, Route.Settings)
val mainRoutes = listOf(Route.Welcome, Route.Home, Route.Settings)

val welcomeRoutes = listOf(Route.ServerDetails, Route.ServerPassword)
val authRoutes = listOf(Route.Home)
val settingRoutes =
    listOf(Route.GeneralSettings, Route.Appearance, Route.Languages, Route.About)
val appearanceRoutes = listOf(Route.Theme, Route.MaterialYou)
val aboutRoutes = listOf(Route.Licenses)

val allRoutes =
    mainRoutes + welcomeRoutes + authRoutes + settingRoutes + appearanceRoutes + aboutRoutes

fun List<Route>.findByDestination(destination: String): Route =
    allRoutes.find { route: Route -> route.destination == destination } ?: Route.Home