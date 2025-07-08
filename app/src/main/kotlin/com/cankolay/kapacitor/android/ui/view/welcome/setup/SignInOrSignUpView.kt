package com.cankolay.kapacitor.android.ui.view.welcome.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.cankolay.kapacitor.android.ui.navigation.signInView
import com.cankolay.kapacitor.android.ui.navigation.signUpView

@Composable
fun SignInOrSignUpView() {
    val navController = LocalNavController.current

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.Bottom,
        ) {
            Button(onClick = {
                navController.navigate(route = signInView)
            }) {
                Text(text = stringResource(id = R.string.auth_sign_in))
            }

            Button(onClick = {
                navController.navigate(route = signUpView)
            }) {
                Text(text = stringResource(id = R.string.auth_sign_up))
            }
        }
    }
}