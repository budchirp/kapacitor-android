package com.cankolay.kapacitor.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import com.cankolay.kapacitor.android.ui.composable.animatedComposable
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.view.HomeView
import com.cankolay.kapacitor.android.ui.view.auth.SignInView
import com.cankolay.kapacitor.android.ui.view.auth.SignUpView
import com.cankolay.kapacitor.android.ui.view.notes.NoteView
import com.cankolay.kapacitor.android.ui.view.notes.NotesView
import com.cankolay.kapacitor.android.ui.view.settings.LanguagesView
import com.cankolay.kapacitor.android.ui.view.settings.ServerSettingsView
import com.cankolay.kapacitor.android.ui.view.settings.SettingsView
import com.cankolay.kapacitor.android.ui.view.settings.about.AboutView
import com.cankolay.kapacitor.android.ui.view.settings.about.LicensesView
import com.cankolay.kapacitor.android.ui.view.settings.appearance.AppearanceView
import com.cankolay.kapacitor.android.ui.view.settings.appearance.MaterialYouView
import com.cankolay.kapacitor.android.ui.view.settings.appearance.ThemeView
import com.cankolay.kapacitor.android.ui.view.user.profile.ProfileView
import com.cankolay.kapacitor.android.ui.view.welcome.WelcomeView
import com.cankolay.kapacitor.android.ui.view.welcome.setup.ServerDetailsView
import com.cankolay.kapacitor.android.ui.view.welcome.setup.ServerPasswordView
import com.cankolay.kapacitor.android.ui.view.welcome.setup.SignInOrSignUpView
import com.cankolay.kapacitor.android.viewmodel.AppViewModel

@Composable
fun AppNavGraph(appViewModel: AppViewModel = hiltViewModel<AppViewModel>()) {
    val appState by appViewModel.appDataStoreFlow.collectAsState()

    val navController = LocalNavController.current
    NavHost(
        navController = navController,
        startDestination = if (appState.isSetupDone) homeView else welcomeView,
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
