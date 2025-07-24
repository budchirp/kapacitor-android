package com.cankolay.kapacitor.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import com.cankolay.kapacitor.android.presentation.composable.animatedComposable
import com.cankolay.kapacitor.android.presentation.composition.LocalNavController
import com.cankolay.kapacitor.android.presentation.view.HomeView
import com.cankolay.kapacitor.android.presentation.view.auth.SignInView
import com.cankolay.kapacitor.android.presentation.view.auth.SignUpView
import com.cankolay.kapacitor.android.presentation.view.notes.NoteView
import com.cankolay.kapacitor.android.presentation.view.notes.NotesView
import com.cankolay.kapacitor.android.presentation.view.settings.LanguagesView
import com.cankolay.kapacitor.android.presentation.view.settings.ServerSettingsView
import com.cankolay.kapacitor.android.presentation.view.settings.SettingsView
import com.cankolay.kapacitor.android.presentation.view.settings.about.AboutView
import com.cankolay.kapacitor.android.presentation.view.settings.about.LicensesView
import com.cankolay.kapacitor.android.presentation.view.settings.appearance.AppearanceView
import com.cankolay.kapacitor.android.presentation.view.settings.appearance.MaterialYouView
import com.cankolay.kapacitor.android.presentation.view.settings.appearance.ThemeView
import com.cankolay.kapacitor.android.presentation.view.user.profile.ProfileView
import com.cankolay.kapacitor.android.presentation.view.welcome.WelcomeView
import com.cankolay.kapacitor.android.presentation.view.welcome.setup.ServerDetailsView
import com.cankolay.kapacitor.android.presentation.view.welcome.setup.ServerPasswordView
import com.cankolay.kapacitor.android.presentation.view.welcome.setup.SignInOrSignUpView
import com.cankolay.kapacitor.android.presentation.viewmodel.AppViewModel

@Composable
fun AppNavGraph(appViewModel: AppViewModel = hiltViewModel<AppViewModel>()) {
    val appState by appViewModel.appDataStoreFlow.collectAsState()

    val navController = LocalNavController.current
    NavHost(
        navController = navController,
        startDestination = if (appState.isSetupDone) Route.Home else Route.Welcome,
    ) {
        animatedComposable<Route.Welcome> {
            WelcomeView()
        }

        animatedComposable<Route.ServerDetails> {
            ServerDetailsView()
        }

        animatedComposable<Route.ServerPassword> {
            ServerPasswordView()
        }

        animatedComposable<Route.SignInOrSignUp> {
            SignInOrSignUpView()
        }

        animatedComposable<Route.SignIn> {
            SignInView()
        }

        animatedComposable<Route.SignUp> {
            SignUpView()
        }

        animatedComposable<Route.Profile> {
            ProfileView()
        }

        animatedComposable<Route.Home> {
            HomeView()
        }

        animatedComposable<Route.Settings> {
            SettingsView()
        }

        animatedComposable<Route.ServerSettings> {
            ServerSettingsView()
        }

        animatedComposable<Route.Languages> {
            LanguagesView()
        }

        animatedComposable<Route.Appearance> {
            AppearanceView()
        }
        animatedComposable<Route.Theme> {
            ThemeView()
        }
        animatedComposable<Route.MaterialYou> {
            MaterialYouView()
        }

        animatedComposable<Route.About> {
            AboutView()
        }

        animatedComposable<Route.Licenses> {
            LicensesView()
        }

        animatedComposable<Route.Notes> {
            NotesView()
        }

        animatedComposable<Route.Note>(defaultLayout = false) { backStackEntry ->
            val route = backStackEntry.toRoute<Route.Note>()
            NoteView(id = route.id)
        }
    }
}
