package com.cankolay.kapacitor.android.ui.views.settings.appearance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cankolay.kapacitor.android.ui.composables.Icon
import com.cankolay.kapacitor.android.ui.composables.ListItem
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.navigation.appearanceRoutes

@Composable
fun AppearanceView() {
    val navController = LocalNavController.current

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(appearanceRoutes) { route ->
                ListItem(
                    title = stringResource(id = route.title),
                    description =
                        stringResource(
                            id = route.description
                        ),
                    onClick = {
                        navController.navigate(route = route.destination)
                    },
                    firstItem = {
                        Icon(
                            icon = route.icon,
                        )
                    },
                )
            }
        }
    }
}