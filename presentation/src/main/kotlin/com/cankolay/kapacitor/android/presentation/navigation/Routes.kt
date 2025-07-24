package com.cankolay.kapacitor.android.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Colorize
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Note
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.ui.graphics.vector.ImageVector
import com.cankolay.kapacitor.android.presentation.R
import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    object Welcome : Route()

    @Serializable
    object ServerDetails : Route()

    @Serializable
    object ServerPassword : Route()

    @Serializable
    object SignInOrSignUp : Route()

    @Serializable
    object SignIn : Route()

    @Serializable
    object SignUp : Route()

    @Serializable
    object Profile : Route()

    @Serializable
    object Home : Route()

    @Serializable
    object Settings : Route()

    @Serializable
    object ServerSettings : Route()

    @Serializable
    object Appearance : Route()

    @Serializable
    object Theme : Route()

    @Serializable
    object MaterialYou : Route()

    @Serializable
    object Languages : Route()

    @Serializable
    object About : Route()

    @Serializable
    object Licenses : Route()

    @Serializable
    object Notes : Route()

    @Serializable
    data class Note(val id: String = "") : Route()
}

data class RouteInfo(
    val title: Int,
    val description: Int,
    val icon: ImageVector,
    val outlinedIcon: ImageVector
)

val routeInfos = mapOf(
    Route.Welcome to RouteInfo(
        title = R.string.welcome,
        description = R.string.empty,
        icon = Icons.Filled.Home,
        outlinedIcon = Icons.Outlined.Home
    ),
    Route.ServerDetails to RouteInfo(
        title = R.string.server_details,
        description = R.string.empty,
        icon = Icons.Filled.Settings,
        outlinedIcon = Icons.Outlined.Settings
    ),
    Route.ServerPassword to RouteInfo(
        title = R.string.server_password,
        description = R.string.empty,
        icon = Icons.Filled.Settings,
        outlinedIcon = Icons.Outlined.Settings
    ),
    Route.SignInOrSignUp to RouteInfo(
        title = R.string.auth_sign_in_or_sign_up,
        description = R.string.empty,
        icon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person
    ),
    Route.SignIn to RouteInfo(
        title = R.string.auth_sign_in,
        description = R.string.auth_sign_in_desc,
        icon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person
    ),
    Route.SignUp to RouteInfo(
        title = R.string.auth_sign_up,
        description = R.string.auth_sign_up_desc,
        icon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person
    ),
    Route.Profile to RouteInfo(
        title = R.string.profile,
        description = R.string.profile_desc,
        icon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person
    ),
    Route.Home to RouteInfo(
        title = R.string.home,
        description = R.string.empty,
        icon = Icons.Filled.Home,
        outlinedIcon = Icons.Outlined.Home
    ),
    Route.Settings to RouteInfo(
        title = R.string.settings,
        description = R.string.empty,
        icon = Icons.Filled.Settings,
        outlinedIcon = Icons.Outlined.Settings
    ),
    Route.ServerSettings to RouteInfo(
        title = R.string.server_settings,
        description = R.string.server_settings_desc,
        icon = Icons.Filled.Settings,
        outlinedIcon = Icons.Outlined.Settings
    ),
    Route.Appearance to RouteInfo(
        title = R.string.appearance,
        description = R.string.appearance_desc,
        icon = Icons.Filled.Palette,
        outlinedIcon = Icons.Outlined.Palette
    ),
    Route.Theme to RouteInfo(
        title = R.string.appearance_theme,
        description = R.string.appearance_theme_desc,
        icon = Icons.Filled.DarkMode,
        outlinedIcon = Icons.Outlined.DarkMode
    ),
    Route.MaterialYou to RouteInfo(
        title = R.string.appearance_material_you,
        description = R.string.appearance_material_you_desc,
        icon = Icons.Filled.Colorize,
        outlinedIcon = Icons.Outlined.Colorize
    ),
    Route.Languages to RouteInfo(
        title = R.string.languages,
        description = R.string.languages_desc,
        icon = Icons.Filled.Translate,
        outlinedIcon = Icons.Outlined.Translate
    ),
    Route.About to RouteInfo(
        title = R.string.about,
        description = R.string.about_desc,
        icon = Icons.Filled.Info,
        outlinedIcon = Icons.Outlined.Info
    ),
    Route.Licenses to RouteInfo(
        title = R.string.licenses,
        description = R.string.licenses_desc,
        icon = Icons.Filled.Gavel,
        outlinedIcon = Icons.Outlined.Gavel
    ),
    Route.Notes to RouteInfo(
        title = R.string.notes,
        description = R.string.notes,
        icon = Icons.Filled.Note,
        outlinedIcon = Icons.Outlined.Note
    )
)

val drawerRoutes = listOf(Route.Home, Route.Settings)
val mainRoutes = listOf(Route.Welcome, Route.Home, Route.Settings)
val featureRoutes = listOf(Route.Notes)

val settingRoutes =
    listOf(Route.Profile, Route.ServerSettings, Route.Appearance, Route.Languages, Route.About)
val appearanceRoutes = listOf(Route.Theme, Route.MaterialYou)
val aboutRoutes = listOf(Route.Licenses)
