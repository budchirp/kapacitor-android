package com.cankolay.kapacitor.android.ui.view.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cankolay.kapacitor.android.R
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.navigation.serverDetailsView

@Composable
fun WelcomeView() {
    val navController = LocalNavController.current

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            Button(onClick = {
                navController.navigate(route = serverDetailsView)
            }) {
                Text(text = stringResource(id = R.string.welcome_lets_get_started))
            }
        }
    }
}