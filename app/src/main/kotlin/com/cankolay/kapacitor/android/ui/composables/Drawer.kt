package com.cankolay.kapacitor.android.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.core.layout.WindowWidthSizeClass
import com.cankolay.kapacitor.android.R
import com.cankolay.kapacitor.android.ui.composition.LocalDrawerState
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.navigation.drawerRoutes
import com.cankolay.kapacitor.android.ui.navigation.routeInfos
import com.cankolay.kapacitor.android.ui.navigation.settingsView
import com.cankolay.kapacitor.android.ui.utils.getSystemRoundedCorners
import com.cankolay.kapacitor.android.viewmodel.AppViewModel
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    appViewModel: AppViewModel = hiltViewModel<AppViewModel>(),
    content: @Composable () -> Unit,
) {
    val isDrawerEnabled by appViewModel.isDrawerEnabled.collectAsState()
    if (isDrawerEnabled) {
        val drawerState = LocalDrawerState.current

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        when (windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT, WindowWidthSizeClass.MEDIUM ->
                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet(
                            modifier =
                                Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(fraction = 0.85f)
                                    .clip(
                                        shape =
                                            RoundedCornerShape(
                                                size = getSystemRoundedCorners(),
                                            ),
                                    ),
                            drawerState = drawerState,
                        ) {
                            DrawerContent()
                        }
                    },
                    gesturesEnabled = true,
                    drawerState = drawerState,
                ) {
                    content()
                }

            WindowWidthSizeClass.EXPANDED ->
                PermanentNavigationDrawer(drawerContent = {
                    PermanentDrawerSheet(
                        modifier =
                            Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(fraction = 0.35f),
                        drawerContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    ) {
                        DrawerContent()
                    }
                }) {
                    content()
                }
        }
    } else {
        content()
    }
}

@Composable
fun DrawerContent() {
    val navController = LocalNavController.current

    val coroutineScope = rememberCoroutineScope()
    val drawerState = LocalDrawerState.current

    val windowSizeClass =
        currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass

    LaunchedEffect(
        key1 = navController.currentBackStackEntryAsState().value?.destination?.route
            ?: navController.currentBackStackEntryAsState().value
    ) {
        if (windowSizeClass != WindowWidthSizeClass.EXPANDED) {
            coroutineScope.launch {
                drawerState.apply {
                    close()
                }
            }
        }
    }

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(height = 64.dp)
                .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
        )

        if (windowSizeClass != WindowWidthSizeClass.EXPANDED) {
            IconButton(onClick = {
                coroutineScope.launch {
                    drawerState.apply {
                        close()
                    }
                }
            }) {
                Icon(
                    icon = Icons.Filled.Close,
                )
            }
        }
    }

    Column(
        modifier =
            Modifier
                .padding(vertical = 16.dp)
                .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination =
            navBackStackEntry?.destination?.route

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                drawerRoutes.map { route ->
                    val routeInfo = routeInfos[route]!!
                    val selected =
                        currentDestination?.contains(route.id, ignoreCase = true) ?: false

                    NavigationDrawerItem(
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        selected = selected,
                        onClick = {
                            if (currentDestination != route.id) {
                                navController.navigate(
                                    route = route,
                                )
                            }
                        },
                        label = { Text(text = stringResource(id = routeInfo.title)) },
                        icon = {
                            Icon(icon = if (selected) routeInfo.icon else routeInfo.outlinedIcon)
                        },
                    )
                }
            }

            HorizontalDivider()

            NavigationDrawerItem(
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                label = {
                    Text(text = stringResource(id = R.string.users_change_user))
                },
                selected = true,
                icon = {
                    Icon(icon = routeInfos[settingsView]!!.icon)
                },
                onClick = {
                    if (windowSizeClass != WindowWidthSizeClass.EXPANDED) {
                        coroutineScope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                    }
                },
            )
        }
    }
}