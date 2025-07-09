package com.cankolay.kapacitor.android.ui.view.welcome.setup

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
import androidx.compose.runtime.collectAsState
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
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.ui.composable.Icon
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.navigation.signInOrSignUpView
import com.cankolay.kapacitor.android.viewmodel.AppViewModel
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerPasswordViewModel
import kotlinx.coroutines.launch

@Composable
fun ServerPasswordView(
    appViewModel: AppViewModel = hiltViewModel<AppViewModel>(),
    serverPasswordViewModel: ServerPasswordViewModel = hiltViewModel<ServerPasswordViewModel>()
) {
    val context = LocalContext.current
    val navController = LocalNavController.current

    val data by serverPasswordViewModel.data.collectAsState()
    val errors by serverPasswordViewModel.errors.collectAsState()

    val isLoading by serverPasswordViewModel.isLoading.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .weight(weight = 1f),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                ) {
                    var isPasswordHidden by remember {
                        mutableStateOf(value = true)
                    }

                    val inputErrors = errors["password"]

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
                        isError = inputErrors?.isNotEmpty() ?: false,
                        onValueChange = { text ->
                            serverPasswordViewModel.updateData(newData = data.copy(password = text))
                        },
                    )

                    inputErrors?.forEach { error ->
                        Text(
                            text = stringResource(
                                id = context.resources.getIdentifier(
                                    "input_${error.message}",
                                    "string",
                                    context.packageName
                                ), stringResource(
                                    id = context.resources.getIdentifier(
                                        "server_${error.property}",
                                        "string",
                                        context.packageName
                                    )
                                )
                            )
                        )
                    }
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
            stringResource(id = R.string.server_invalid_credentials)

            Button(onClick = {
                if (serverPasswordViewModel.validateData().isEmpty()) {
                    coroutineScope.launch {
                        val result = serverPasswordViewModel.submit()
                        when (result) {
                            is ApiResult.Success -> {
                                appViewModel.setIsDrawerEnabled(enable = true)

                                navController.navigate(route = signInOrSignUpView)
                            }

                            is ApiResult.Error -> {
                                Toast.makeText(
                                    context,
                                    result.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                Toast.makeText(
                                    context,
                                    serverFail,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }, enabled = errors.isEmpty() || !isLoading) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}