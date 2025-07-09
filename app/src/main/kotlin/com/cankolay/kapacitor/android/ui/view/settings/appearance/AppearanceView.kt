package com.cankolay.kapacitor.android.ui.view.settings.appearance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cankolay.kapacitor.android.ui.composable.Icon
import com.cankolay.kapacitor.android.ui.composable.ListItem
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.navigation.appearanceRoutes
import com.cankolay.kapacitor.android.ui.navigation.routeInfos

@Composable
fun AppearanceView() {
    val navController = LocalNavController.current

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = appearanceRoutes) { route ->
                val routeInfo = routeInfos[route]!!
                ListItem(
                    title = stringResource(id = routeInfo.title),
                    description =
                        stringResource(
                            id = routeInfo.description
                        ),
                    onClick = {
                        navController.navigate(route = route)
                    },
                    firstItem = {
                        Icon(
                            icon = routeInfo.icon,
                        )
                    },
                )
            }
        }
    }
}