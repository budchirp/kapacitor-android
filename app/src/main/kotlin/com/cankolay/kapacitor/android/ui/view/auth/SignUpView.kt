package com.cankolay.kapacitor.android.ui.view.auth

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
import androidx.compose.material.icons.filled.Person
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
import com.cankolay.kapacitor.android.ui.navigation.signInView
import com.cankolay.kapacitor.android.viewmodel.auth.SignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpView(
    signUpViewModel: SignUpViewModel = hiltViewModel<SignUpViewModel>()
) {
    val context = LocalContext.current
    val navController = LocalNavController.current

    val data by signUpViewModel.data.collectAsState()
    val errors by signUpViewModel.errors.collectAsState()

    val isLoading by signUpViewModel.isLoading.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
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
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                ) {
                    val inputErrors = errors["username"]

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = data.username,
                        label = {
                            Text(text = stringResource(id = R.string.auth_username))
                        },
                        leadingIcon = {
                            Icon(icon = Icons.Filled.Person)
                        },
                        singleLine = true,
                        isError = inputErrors?.isNotEmpty() ?: false,
                        onValueChange = { text ->
                            signUpViewModel.updateData(newData = data.copy(username = text))
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
                                        "auth_${error.property}",
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
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                ) {
                    var isPasswordHidden by remember {
                        mutableStateOf(value = true)
                    }

                    val inputErrors = errors["password"]

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = data.password,
                        label = {
                            Text(text = stringResource(id = R.string.auth_password))
                        },
                        leadingIcon = {
                            Icon(icon = Icons.Filled.Lock)
                        },
                        trailingIcon = {
                            IconButton(onClick = { isPasswordHidden = !isPasswordHidden }) {
                                Icon(icon = if (isPasswordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff)
                            }
                        },
                        singleLine = true,
                        isError = inputErrors?.isNotEmpty() ?: false,
                        visualTransformation = if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        onValueChange = { text ->
                            signUpViewModel.updateData(newData = data.copy(password = text))
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
                                        "auth_${error.property}",
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
                if (signUpViewModel.validateData().isEmpty()) {
                    coroutineScope.launch {
                        val result = signUpViewModel.submit()
                        when (result) {
                            is ApiResult.Success -> {
                                navController.navigate(route = signInView)
                            }

                            is ApiResult.Error -> {
                                Toast.makeText(
                                    context,
                                    result.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is ApiResult.Fatal -> {
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
                Text(text = stringResource(id = R.string.submit))
            }
        }
    }
}