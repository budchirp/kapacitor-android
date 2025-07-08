package com.cankolay.kapacitor.android.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.cankolay.kapacitor.android.ui.composition.LocalDrawerState
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.navigation.Route
import com.cankolay.kapacitor.android.ui.navigation.mainRoutes
import com.cankolay.kapacitor.android.ui.navigation.routeInfos
import com.cankolay.kapacitor.android.viewmodel.AppViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout(
    appViewModel: AppViewModel = hiltViewModel<AppViewModel>(),
    route: Route? = null,
    title: String? = null,
    content: @Composable () -> Unit,
) {
    val navController = LocalNavController.current
    val drawerState = LocalDrawerState.current

    val coroutineScope = rememberCoroutineScope()

    val isDrawerEnabled by appViewModel.isDrawerEnabled.collectAsState()

    val scrollBehavior: TopAppBarScrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            state = rememberTopAppBarState(),
            canScroll = { true },
        )

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars.only(sides = WindowInsetsSides.Vertical + WindowInsetsSides.End),
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            val routeInfo = routeInfos[route]
            LargeTopAppBar(
                title = {
                    Text(
                        text = title
                            ?: if (routeInfo?.title != null) stringResource(
                                id = routeInfo.title
                            ) else "",
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    var showBack = false
                    var showMenu = true

                    run {
                        mainRoutes.forEach { _route ->
                            if (route == _route) {
                                showBack = false
                                showMenu = isDrawerEnabled

                                return@run
                            } else {
                                showBack = true
                                showMenu = false
                            }
                        }
                    }

                    val windowSizeClass =
                        currentWindowAdaptiveInfo().windowSizeClass
                    if (windowSizeClass.windowWidthSizeClass ==
                        WindowWidthSizeClass.EXPANDED
                    ) {
                        showMenu =
                            false
                    }

                    if (showBack) {
                        IconButton(onClick = {
                            with(navController) {
                                if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                                    popBackStack()
                                }
                            }
                        }) {
                            Icon(
                                icon = Icons.AutoMirrored.Filled.ArrowBack,
                            )
                        }
                    }

                    if (showMenu) {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                icon = Icons.Filled.Menu,
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues = innerPadding,
                    ),
        ) { content() }
    }
}