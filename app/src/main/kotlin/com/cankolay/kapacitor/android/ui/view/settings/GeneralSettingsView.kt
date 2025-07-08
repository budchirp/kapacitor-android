package com.cankolay.kapacitor.android.ui.view.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cankolay.kapacitor.android.R
import com.cankolay.kapacitor.android.ui.composable.ListItem
import com.cankolay.kapacitor.android.viewmodel.SettingsViewModel

@Composable
fun GeneralSettingsView(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    var isOpen by remember {
        mutableStateOf(value = false)
    }

    if (isOpen) {
        AlertDialog(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
            title = {
                Text(text = "")
            },
            onDismissRequest = { isOpen = false },
            confirmButton = {
                Button(onClick = {
                    isOpen = false
                }) {
                    Text(text = stringResource(id = R.string.save))
                }
            },
            dismissButton = {
                FilledTonalButton(onClick = { isOpen = false }) {
                    Text(text = stringResource(id = R.string.close))
                }
            },
            text = {
            },
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            item {
                ListItem(
                    title = "Example field",
                    description = "Example field that opens a dialog",
                    onClick = {
                        isOpen = true
                    },
                )
            }
        }
    }
}