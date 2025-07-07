package com.cankolay.kapacitor.android.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.cankolay.kapacitor.android.R
import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    abstract val id: String

    @Serializable
    data class Welcome(
        override val id: String = "welcome",
    ) : Route()

    @Serializable
    data class ServerDetails(
        override val id: String = "server_details",
    ) : Route()

    @Serializable
    data class ServerPassword(
        override val id: String = "server_password",
    ) : Route()

    @Serializable
    data class SignIn(
        override val id: String = "auth/signin",
    ) : Route()

    @Serializable
    data class SignUp(
        override val id: String = "auth/signup",
    ) : Route()

    @Serializable
    data class Home(
        override val id: String = "home",
    ) : Route()

    @Serializable
    data class Settings(
        override val id: String = "settings",
    ) : Route()

    @Serializable
    data class GeneralSettings(
        override val id: String = "settings/general",
    ) : Route()

    @Serializable
    data class Appearance(
        override val id: String = "settings/appearance",
    ) : Route()

    @Serializable
    data class Theme(
        override val id: String = "settings/appearance/theme",
    ) : Route()

    @Serializable
    data class MaterialYou(
        override val id: String = "settings/appearance/material-you",
    ) : Route()

    @Serializable
    data class Languages(
        override val id: String = "settings/languages",
    ) : Route()

    @Serializable
    data class About(
        override val id: String = "settings/about",
    ) : Route()

    @Serializable
    data class Licenses(
        override val id: String = "settings/about/licenses",
    ) : Route()
}

data class RouteInfo(
    val title: Int,
    val description: Int,
    val icon: ImageVector,
    val outlinedIcon: ImageVector
)

val welcomeView = Route.Welcome()
val serverDetailsView = Route.ServerDetails()
val serverPasswordView = Route.ServerPassword()
val homeView = Route.Home()
val settingsView = Route.Settings()
val generalSettingsView = Route.GeneralSettings()
val appearanceView = Route.Appearance()
val themeView = Route.Theme()
val materialYouView = Route.MaterialYou()
val languagesView = Route.Languages()
val aboutView = Route.About()
val licensesView = Route.Licenses()
val signInView = Route.SignIn()
val signUpView = Route.SignUp()

val routeInfos = mapOf(
    welcomeView to RouteInfo(
        title = R.string.welcome,
        description = R.string.empty,
        icon = Icons.Filled.Home,
        outlinedIcon = Icons.Outlined.Home
    ),
    serverDetailsView to RouteInfo(
        title = R.string.server_details,
        description = R.string.empty,
        icon = Icons.Filled.Settings,
        outlinedIcon = Icons.Outlined.Settings
    ),
    serverPasswordView to RouteInfo(
        title = R.string.server_password,
        description = R.string.empty,
        icon = Icons.Filled.Settings,
        outlinedIcon = Icons.Outlined.Settings
    ),
    signInView to RouteInfo(
        title = R.string.sign_in,
        description = R.string.sign_in_desc,
        icon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person
    ),
    signUpView to RouteInfo(
        title = R.string.sign_up,
        description = R.string.sign_up_desc,
        icon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person
    ),
    homeView to RouteInfo(
        title = R.string.home,
        description = R.string.empty,
        icon = Icons.Filled.Home,
        outlinedIcon = Icons.Outlined.Home
    ),
    settingsView to RouteInfo(
        title = R.string.settings,
        description = R.string.empty,
        icon = Icons.Filled.Settings,
        outlinedIcon = Icons.Outlined.Settings
    ),
    generalSettingsView to RouteInfo(
        title = R.string.general_settings,
        description = R.string.general_settings_desc,
        icon = Icons.Filled.Settings,
        outlinedIcon = Icons.Outlined.Settings
    ),
    appearanceView to RouteInfo(
        title = R.string.appearance,
        description = R.string.appearance_desc,
        icon = Icons.Filled.Palette,
        outlinedIcon = Icons.Filled.Palette
    ),
    themeView to RouteInfo(
        title = R.string.appearance_theme,
        description = R.string.appearance_theme_desc,
        icon = Icons.Filled.DarkMode,
        outlinedIcon = Icons.Filled.DarkMode
    ),
    materialYouView to RouteInfo(
        title = R.string.appearance_material_you,
        description = R.string.appearance_material_you_desc,
        icon = Icons.Filled.Colorize,
        outlinedIcon = Icons.Filled.Colorize
    ),
    languagesView to RouteInfo(
        title = R.string.languages,
        description = R.string.languages_desc,
        icon = Icons.Filled.Translate,
        outlinedIcon = Icons.Filled.Translate
    ),
    aboutView to RouteInfo(
        title = R.string.about,
        description = R.string.about_desc,
        icon = Icons.Filled.Info,
        outlinedIcon = Icons.Filled.Info
    ),
    licensesView to RouteInfo(
        title = R.string.licenses,
        description = R.string.licenses_desc,
        icon = Icons.Filled.Gavel,
        outlinedIcon = Icons.Filled.Gavel
    )
)

val drawerRoutes = listOf(homeView, settingsView)
val mainRoutes = listOf(welcomeView, homeView, settingsView)

val welcomeRoutes = listOf(serverDetailsView, serverPasswordView)
val authRoutes = listOf(signInView, signUpView)
val settingRoutes =
    listOf(generalSettingsView, appearanceView, languagesView, aboutView)
val appearanceRoutes = listOf(themeView, materialYouView)
val aboutRoutes = listOf(licensesView)

val allRoutes: List<Route> =
    mainRoutes + welcomeRoutes + authRoutes + settingRoutes + appearanceRoutes + aboutRoutes + signInView

fun List<Route>.findByDestination(id: String): Route =
    allRoutes.find { route: Route -> route.id == id } ?: homeView