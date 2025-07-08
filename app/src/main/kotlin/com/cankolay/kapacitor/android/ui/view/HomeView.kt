package com.cankolay.kapacitor.android.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cankolay.kapacitor.android.ui.composable.GridCard
import com.cankolay.kapacitor.android.ui.navigation.featureRoutes

@Composable
fun HomeView() {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
            columns = GridCells.Fixed(count = 2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = featureRoutes) { route ->
                GridCard(route = route)
            }
        }
    }
}