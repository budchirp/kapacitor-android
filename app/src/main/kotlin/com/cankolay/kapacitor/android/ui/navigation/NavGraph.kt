package com.cankolay.kapacitor.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.cankolay.kapacitor.android.ui.composables.animatedComposableBuilder
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.views.HomeView
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
    val appState by appViewModel.getAppDataStore()

    val navController = LocalNavController.current
    NavHost(
        navController = navController,
        startDestination = if (appState.isSetupDone) Route.Home.destination else Route.Welcome.destination,
    ) {
        val animatedComposable =
            animatedComposableBuilder()

        navigation(
            startDestination = Route.Welcome.destination,
            route = "_${Route.Welcome.destination}",
        ) {
            animatedComposable(
                Route.Welcome.destination,
                null,
                null,
            ) {
                WelcomeView()
            }

            animatedComposable(
                Route.ServerDetails.destination,
                null,
                null,
            ) {
                ServerDetailsView()
            }

            animatedComposable(
                Route.ServerPassword.destination,
                null,
                null,
            ) {
                ServerPasswordView(
                )
            }
        }

        animatedComposable(
            Route.Home.destination,
            null,
            null,
        ) {
            HomeView()
        }

        navigation(
            startDestination = Route.Settings.destination,
            route = "_${Route.Settings.destination}",
        ) {
            animatedComposable(
                Route.Settings.destination,
                null,
                null,
            ) {
                SettingsView()
            }

            animatedComposable(
                Route.GeneralSettings.destination,
                null,
                null,
            ) {
                GeneralSettingsView()
            }

            animatedComposable(
                Route.Languages.destination,
                null,
                null,
            ) {
                LanguagesView()
            }

            navigation(
                startDestination = Route.Appearance.destination,
                route = "_${Route.Appearance.destination}",
            ) {
                animatedComposable(
                    Route.Appearance.destination,
                    null,
                    null,
                ) {
                    AppearanceView()
                }
                animatedComposable(
                    Route.Theme.destination,
                    null,
                    null,
                ) {
                    ThemeView()
                }
                animatedComposable(
                    Route.MaterialYou.destination,
                    null,
                    null,
                ) {
                    MaterialYouView()
                }
            }

            navigation(
                startDestination = Route.About.destination,
                route = "_${Route.About.destination}",
            ) {
                animatedComposable(
                    Route.About.destination,
                    null,
                    null,
                ) {
                    AboutView()
                }
                animatedComposable(
                    Route.Licenses.destination,
                    null,
                    null,
                ) {
                    LicensesView()
                }
            }
        }
    }
}