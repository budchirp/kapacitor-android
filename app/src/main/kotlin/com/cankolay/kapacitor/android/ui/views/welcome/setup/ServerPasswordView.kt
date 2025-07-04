package com.cankolay.kapacitor.android.ui.views.welcome.setup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cankolay.kapacitor.android.R
import com.cankolay.kapacitor.android.ui.composables.Icon
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.navigation.Route
import com.cankolay.kapacitor.android.viewmodel.AppViewModel
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerPasswordSubmitReturn.InvalidCredentials
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerPasswordSubmitReturn.Success
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerPasswordViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun ServerPasswordView(
    appViewModel: AppViewModel = hiltViewModel<AppViewModel>(),
    serverPasswordViewModel: ServerPasswordViewModel = hiltViewModel<ServerPasswordViewModel>()
) {
    val context = LocalContext.current
    val navController = LocalNavController.current

    val data = serverPasswordViewModel.data
    val validationError = serverPasswordViewModel.validationError

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        if (serverPasswordViewModel.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(all = 16.dp),
                ) {
                    var isPasswordHidden by remember {
                        mutableStateOf(value = true)
                    }

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = data.password,
                        label = {
                            Text(text = stringResource(id = R.string.server_password))
                        },
                        leadingIcon = {
                            Icon(icon = Icons.Filled.Lock)
                        },
                        trailingIcon = {
                            IconButton(onClick = { isPasswordHidden = !isPasswordHidden }) {
                                Icon(icon = if (isPasswordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff)
                            }
                        },
                        visualTransformation = if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = validationError?.property == "password",
                        onValueChange = { text: String ->
                            serverPasswordViewModel.updateData(newData = data.copy(password = text))
                        },
                    )

                    if (validationError != null && validationError.property == "password") Text(
                        text = stringResource(
                            id = context.resources.getIdentifier(
                                "input_${validationError.message}",
                                "string",
                                context.packageName
                            ), stringResource(
                                id = context.resources.getIdentifier(
                                    "server_${validationError.property}",
                                    "string",
                                    context.packageName
                                )
                            )
                        )
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            val serverFail = stringResource(id = R.string.server_fail)
            val invalidCredentials =
                stringResource(id = R.string.server_invalid_credentials)

            Button(onClick = {
                if (serverPasswordViewModel.validateData() == null) {
                    coroutineScope.launch {
                        val result =
                            runBlocking { serverPasswordViewModel.submit() }
                        if (result == Success) {
                            appViewModel.setIsDrawerEnabled(enable = true)

                            navController.navigate(route = Route.Home.destination)
                        } else {
                            Toast.makeText(
                                context,
                                if (result == InvalidCredentials) invalidCredentials else serverFail,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }, enabled = validationError == null) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}