package com.cankolay.kapacitor.android.presentation.view.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cankolay.kapacitor.android.presentation.R
import com.cankolay.kapacitor.android.presentation.composition.LocalNavController
import com.cankolay.kapacitor.android.presentation.navigation.Route

@Composable
fun WelcomeView() {
    val navController = LocalNavController.current

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier =
                Modifier
                    .padding(all = 16.dp)
                    .fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
        ) {
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                navController.navigate(route = Route.ServerDetails)
            }) {
                Text(text = stringResource(id = R.string.welcome_lets_get_started))
            }
        }
    }
}