package com.cankolay.kapacitor.android.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cankolay.kapacitor.android.R
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.navigation.signInView
import com.cankolay.kapacitor.android.ui.navigation.signUpView

@Composable
fun HomeView() {
    val navController = LocalNavController.current

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(text = stringResource(id = R.string.home))

                    Button(onClick = {
                        navController.navigate(route = signUpView)
                    }) {
                        Text("hi")
                    }


                    Button(onClick = {
                        navController.navigate(route = signInView)
                    }) {
                        Text("hinigga")
                    }
                }
            }
        }
    }
}