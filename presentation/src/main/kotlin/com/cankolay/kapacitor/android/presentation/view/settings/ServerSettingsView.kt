package com.cankolay.kapacitor.android.presentation.view.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Numbers
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cankolay.kapacitor.android.presentation.R
import com.cankolay.kapacitor.android.presentation.composable.Icon
import com.cankolay.kapacitor.android.presentation.viewmodel.settings.ServerSettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun ServerSettingsView(serverSettingsViewModel: ServerSettingsViewModel = hiltViewModel<ServerSettingsViewModel>()) {
    val context = LocalContext.current

    val data by serverSettingsViewModel.data.collectAsState()
    val errors by serverSettingsViewModel.errors.collectAsState()

    val isLoading by serverSettingsViewModel.isLoading.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    val inputErrors = errors["url"]

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = data.url,
                        label = {
                            Text(text = stringResource(id = R.string.server_details_url))
                        },
                        leadingIcon = {
                            Icon(icon = Icons.Filled.Language)
                        },
                        singleLine = true,
                        isError = inputErrors?.isNotEmpty() ?: false,
                        onValueChange = { text ->
                            serverSettingsViewModel.updateData(newData = data.copy(url = text))
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
                                        "server_details_${error.property}",
                                        "string",
                                        context.packageName
                                    )
                                )
                            )
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    val inputErrors = errors["port"]

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = data.port,
                        label = {
                            Text(text = stringResource(id = R.string.server_details_port))
                        },
                        leadingIcon = {
                            Icon(icon = Icons.Filled.Numbers)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = inputErrors?.isNotEmpty() ?: false,
                        onValueChange = { text ->
                            serverSettingsViewModel.updateData(newData = data.copy(port = text))
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
                                        "server_details_${error.property}",
                                        "string",
                                        context.packageName
                                    )
                                )
                            )
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
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
                            serverSettingsViewModel.updateData(newData = data.copy(password = text))
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
                                        "server_details_${error.property}",
                                        "string",
                                        context.packageName
                                    )
                                )
                            )
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    val success = stringResource(id = R.string.success)

                    Button(onClick = {
                        if (serverSettingsViewModel.validateData().isEmpty()) {
                            coroutineScope.launch {
                                serverSettingsViewModel.submit()

                                Toast.makeText(
                                    context,
                                    success,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }, enabled = errors.isEmpty() || !isLoading) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }
            }
        }
    }
}