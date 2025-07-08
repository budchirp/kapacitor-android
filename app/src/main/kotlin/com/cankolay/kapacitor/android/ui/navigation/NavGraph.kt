package com.cankolay.kapacitor.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.cankolay.kapacitor.android.ui.composable.animatedComposable
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.view.HomeView
import com.cankolay.kapacitor.android.ui.view.auth.SignInView
import com.cankolay.kapacitor.android.ui.view.auth.SignUpView
import com.cankolay.kapacitor.android.ui.view.settings.GeneralSettingsView
import com.cankolay.kapacitor.android.ui.view.settings.LanguagesView
import com.cankolay.kapacitor.android.ui.view.settings.SettingsView
import com.cankolay.kapacitor.android.ui.view.settings.about.AboutView
import com.cankolay.kapacitor.android.ui.view.settings.about.LicensesView
import com.cankolay.kapacitor.android.ui.view.settings.appearance.AppearanceView
import com.cankolay.kapacitor.android.ui.view.settings.appearance.MaterialYouView
import com.cankolay.kapacitor.android.ui.view.settings.appearance.ThemeView
import com.cankolay.kapacitor.android.ui.view.welcome.WelcomeView
import com.cankolay.kapacitor.android.ui.view.welcome.setup.ServerDetailsView
import com.cankolay.kapacitor.android.ui.view.welcome.setup.ServerPasswordView
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
