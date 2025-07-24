package com.cankolay.kapacitor.android.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cankolay.kapacitor.android.presentation.composition.LocalNavController
import com.cankolay.kapacitor.android.presentation.navigation.Route
import com.cankolay.kapacitor.android.presentation.navigation.routeInfos

@Composable
fun GridCard(route: Route) {
    val navController = LocalNavController.current

    Card(
        modifier = Modifier
            .aspectRatio(ratio = 1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    navController.navigate(route = route)
                }) {
            Column(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
            ) {
                val routeInfo = routeInfos[route]!!

                Row(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .fillMaxWidth(),
                ) {
                    Icon(
                        modifier = Modifier
                            .size(size = 64.dp)
                            .requiredSize(size = 64.dp),
                        icon = routeInfo.icon,
                    )
                }

                Spacer(modifier = Modifier.weight(weight = 1f))

                Text(text = stringResource(id = routeInfo.title))
            }
        }
    }
}

@Composable
fun GridCard(route: Route, title: String, content: String) {
    val navController = LocalNavController.current

    Card {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    navController.navigate(route = route)
                }) {
            Column(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(space = 4.dp),
            ) {

                Text(text = title)
                Text(text = content, maxLines = 2)
            }
        }
    }
}