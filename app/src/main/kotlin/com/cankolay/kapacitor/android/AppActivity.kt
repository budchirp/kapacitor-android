package com.cankolay.kapacitor.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.navigation.compose.rememberNavController
import com.cankolay.kapacitor.android.domain.usecase.server.ServerConnectionStatus
import com.cankolay.kapacitor.android.ui.composable.AppLayout
import com.cankolay.kapacitor.android.ui.composition.ProvideDrawerState
import com.cankolay.kapacitor.android.ui.composition.ProvideNavController
import com.cankolay.kapacitor.android.ui.navigation.AppNavGraph
import com.cankolay.kapacitor.android.ui.navigation.serverDetailsView
import com.cankolay.kapacitor.android.ui.navigation.serverPasswordView
import com.cankolay.kapacitor.android.ui.navigation.signInOrSignUpView
import com.cankolay.kapacitor.android.ui.theme.AppTheme
import com.cankolay.kapacitor.android.viewmodel.AppViewModel
import com.cankolay.kapacitor.android.viewmodel.user.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    private val appViewModel by viewModels<AppViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                appViewModel.isLoading.value || profileViewModel.isLoading.value
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(
            window.decorView,
        ) { view, insets ->
            view.setPadding(0, 0, 0, 0)
            insets
        }

        enableEdgeToEdge()

        setContent {
            val appState by appViewModel.appDataStoreFlow.collectAsState()
            val status by appViewModel.status.collectAsState()
            
            val user = profileViewModel.user.collectAsState()

            val navController = rememberNavController()

            AppTheme {
                ProvideNavController(navController = navController) {
                    ProvideDrawerState(drawerState = rememberDrawerState(initialValue = Closed)) {
                        when (status) {
                            ServerConnectionStatus.UNREACHABLE -> navController.navigate(route = serverDetailsView)
                            ServerConnectionStatus.INVALID_PASSWORD -> navController.navigate(
                                route = serverPasswordView
                            )

                            ServerConnectionStatus.SUCCESS -> {
                                if (appState.isSetupDone && user.value == null) navController.navigate(
                                    route = signInOrSignUpView
                                )
                            }

                            else -> {}
                        }

                        AppLayout {
                            AppNavGraph()
                        }
                    }
                }
            }
        }
    }
}