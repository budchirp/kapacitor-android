package com.cankolay.kapacitor.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.cankolay.kapacitor.android.ui.composables.animatedComposable
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.views.HomeView
import com.cankolay.kapacitor.android.ui.views.auth.SignInView
import com.cankolay.kapacitor.android.ui.views.auth.SignUpView
import com.cankolay.kapacitor.android.ui.views.settings.GeneralSettingsView
import com.cankolay.kapacitor.android.ui.views.settings.LanguagesView
import com.cankolay.kapacitor.android.ui.views.settings.SettingsView
import com.cankolay.kapacitor.android.ui.views.settings.about.AboutView
import com.cankolay.kapacitor.android.ui.views.settings.about.LicensesView
import com.cankolay.kapacitor.android.ui.views.settings.appearance.AppearanceView
import com.cankolay.kapacitor.android.ui.views.settings.appearance.MaterialYouView
import com.cankolay.kapacitor.android.ui.views.settings.appearance.ThemeView
import com.cankolay.kapacitor.android.ui.views.welcome.WelcomeView
import com.cankolay.kapacitor.android.ui.views.welcome.setup.ServerDetailsView
import com.cankolay.kapacitor.android.ui.views.welcome.setup.ServerPasswordView
import com.cankolay.kapacitor.android.viewmodel.AppViewModel

@Composable
fun AppNavGraph(appViewModel: AppViewModel = hiltViewModel<AppViewModel>()) {
    val appState by appViewModel.appDataStoreFlow.collectAsState()

    val navController = LocalNavController.current
    NavHost(
        navController = navController,
        startDestination = if (appState.isSetupDone) homeView else welcomeView,
    ) {
        animatedComposable<Route.Welcome>(
            route = welcomeView
        ) {
            WelcomeView()
        }

        animatedComposable<Route.ServerDetails>(
            route = serverDetailsView
        ) {
            ServerDetailsView()
        }

        animatedComposable<Route.ServerPassword>(
            route = serverPasswordView
        ) {
            ServerPasswordView()
        }

        animatedComposable<Route.SignIn>(
            route = signInView
        ) {
            SignInView()
        }

        animatedComposable<Route.SignUp>(
            route = signUpView
        ) {
            SignUpView()
        }

        animatedComposable<Route.Home>(
            route = homeView
        ) {
            HomeView()
        }

        animatedComposable<Route.Settings>(
            route = settingsView
        ) {
            SettingsView()
        }

        animatedComposable<Route.GeneralSettings>(
            route = generalSettingsView
        ) {
            GeneralSettingsView()
        }

        animatedComposable<Route.Languages>(
            route = languagesView
        ) {
            LanguagesView()
        }

        animatedComposable<Route.Appearance>(
            route = appearanceView
        ) {
            AppearanceView()
        }
        animatedComposable<Route.Theme>(
            route = themeView
        ) {
            ThemeView()
        }
        animatedComposable<Route.MaterialYou>(
            route = materialYouView
        ) {
            MaterialYouView()
        }

        animatedComposable<Route.About>(
            route = aboutView
        ) {
            AboutView()
        }
        animatedComposable<Route.Licenses>(
            route = licensesView
        ) {
            LicensesView()
        }
    }
}
