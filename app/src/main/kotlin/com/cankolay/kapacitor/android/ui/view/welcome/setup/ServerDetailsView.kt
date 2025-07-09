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
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cankolay.kapacitor.android.R
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.ui.composable.Icon
import com.cankolay.kapacitor.android.ui.composition.LocalNavController
import com.cankolay.kapacitor.android.ui.navigation.serverPasswordView
import com.cankolay.kapacitor.android.viewmodel.welcome.setup.ServerDetailsViewModel
import kotlinx.coroutines.launch

@Composable
fun ServerDetailsView(
    serverDetailsViewModel: ServerDetailsViewModel = hiltViewModel<ServerDetailsViewModel>()
) {
    val context = LocalContext.current
    val navController = LocalNavController.current

    val data by serverDetailsViewModel.data.collectAsState()
    val errors by serverDetailsViewModel.errors.collectAsState()

    val isLoading by serverDetailsViewModel.isLoading.collectAsState()

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
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
                            serverDetailsViewModel.updateData(newData = data.copy(url = text))
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
                        .padding(horizontal = 16.dp),
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
                            serverDetailsViewModel.updateData(newData = data.copy(port = text))
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
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            val serverFail = stringResource(id = R.string.server_fail)

            Button(onClick = {
                if (serverDetailsViewModel.validateData().isEmpty()) {
                    coroutineScope.launch {
                        val result = serverDetailsViewModel.submit()
                        when (result) {
                            is ApiResult.Success -> {
                                navController.navigate(route = serverPasswordView)
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