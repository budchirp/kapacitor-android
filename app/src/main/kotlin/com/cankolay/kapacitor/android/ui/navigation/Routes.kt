package com.cankolay.kapacitor.android.ui.navigation

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
import com.cankolay.kapacitor.android.R
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

val welcomeView = Route.Welcome
val serverDetailsView = Route.ServerDetails
val serverPasswordView = Route.ServerPassword
val signInOrSignUpView = Route.SignInOrSignUp
val signInView = Route.SignIn
val signUpView = Route.SignUp
val profileView = Route.Profile
val homeView = Route.Home
val settingsView = Route.Settings
val serverSettingsView = Route.ServerSettings
val appearanceView = Route.Appearance
val themeView = Route.Theme
val materialYouView = Route.MaterialYou
val languagesView = Route.Languages
val aboutView = Route.About
val licensesView = Route.Licenses
val notesView = Route.Notes

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
    signInOrSignUpView to RouteInfo(
        title = R.string.auth_sign_in_or_sign_up,
        description = R.string.empty,
        icon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person
    ),
    signInView to RouteInfo(
        title = R.string.auth_sign_in,
        description = R.string.auth_sign_in_desc,
        icon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person
    ),
    signUpView to RouteInfo(
        title = R.string.auth_sign_up,
        description = R.string.auth_sign_up_desc,
        icon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person
    ),
    profileView to RouteInfo(
        title = R.string.profile,
        description = R.string.profile_desc,
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
    serverSettingsView to RouteInfo(
        title = R.string.server_settings,
        description = R.string.server_settings_desc,
        icon = Icons.Filled.Settings,
        outlinedIcon = Icons.Outlined.Settings
    ),
    appearanceView to RouteInfo(
        title = R.string.appearance,
        description = R.string.appearance_desc,
        icon = Icons.Filled.Palette,
        outlinedIcon = Icons.Outlined.Palette
    ),
    themeView to RouteInfo(
        title = R.string.appearance_theme,
        description = R.string.appearance_theme_desc,
        icon = Icons.Filled.DarkMode,
        outlinedIcon = Icons.Outlined.DarkMode
    ),
    materialYouView to RouteInfo(
        title = R.string.appearance_material_you,
        description = R.string.appearance_material_you_desc,
        icon = Icons.Filled.Colorize,
        outlinedIcon = Icons.Outlined.Colorize
    ),
    languagesView to RouteInfo(
        title = R.string.languages,
        description = R.string.languages_desc,
        icon = Icons.Filled.Translate,
        outlinedIcon = Icons.Outlined.Translate
    ),
    aboutView to RouteInfo(
        title = R.string.about,
        description = R.string.about_desc,
        icon = Icons.Filled.Info,
        outlinedIcon = Icons.Outlined.Info
    ),
    licensesView to RouteInfo(
        title = R.string.licenses,
        description = R.string.licenses_desc,
        icon = Icons.Filled.Gavel,
        outlinedIcon = Icons.Outlined.Gavel
    ),
    notesView to RouteInfo(
        title = R.string.notes,
        description = R.string.notes,
        icon = Icons.Filled.Note,
        outlinedIcon = Icons.Outlined.Note
    )
)

val drawerRoutes = listOf(homeView, settingsView)
val mainRoutes = listOf(welcomeView, homeView, settingsView)
val featureRoutes = listOf(notesView)

val settingRoutes =
    listOf(profileView, serverSettingsView, appearanceView, languagesView, aboutView)
val appearanceRoutes = listOf(themeView, materialYouView)
val aboutRoutes = listOf(licensesView)