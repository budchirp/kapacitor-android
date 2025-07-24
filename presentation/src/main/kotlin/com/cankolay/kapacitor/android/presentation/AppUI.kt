package com.cankolay.kapacitor.android.presentation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.cankolay.kapacitor.android.domain.usecase.server.ServerConnectionStatus
import com.cankolay.kapacitor.android.presentation.composable.layout.AppLayout
import com.cankolay.kapacitor.android.presentation.composition.ProvideDrawerState
import com.cankolay.kapacitor.android.presentation.composition.ProvideNavController
import com.cankolay.kapacitor.android.presentation.navigation.AppNavGraph
import com.cankolay.kapacitor.android.presentation.navigation.Route
import com.cankolay.kapacitor.android.presentation.theme.AppTheme
import com.cankolay.kapacitor.android.presentation.viewmodel.AppViewModel
import com.cankolay.kapacitor.android.presentation.viewmodel.user.profile.ProfileViewModel

@Composable
fun AppUI(
    appViewModel: AppViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val appState by appViewModel.appDataStoreFlow.collectAsState()
    val status by appViewModel.status.collectAsState()

    val user = profileViewModel.userDto.collectAsState()

    val navController = rememberNavController()

    AppTheme {
        ProvideNavController(navController = navController) {
            ProvideDrawerState(drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)) {
                when (status) {
                    ServerConnectionStatus.UNREACHABLE -> navController.navigate(route = Route.ServerDetails)
                    ServerConnectionStatus.INVALID_PASSWORD -> navController.navigate(
                        route = Route.ServerPassword
                    )

                    ServerConnectionStatus.SUCCESS -> {
                        if (appState.isSetupDone && user.value == null) navController.navigate(
                            route = Route.SignInOrSignUp
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