package com.cankolay.kapacitor.android.presentation.view.settings.appearance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.cankolay.kapacitor.android.data.datastore.model.Theme
import com.cankolay.kapacitor.android.presentation.R
import com.cankolay.kapacitor.android.presentation.composable.ListItem
import com.cankolay.kapacitor.android.presentation.viewmodel.settings.SettingsViewModel

@Composable
fun ThemeView(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val settingsState by settingsViewModel.settingsStateFlow.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = Theme.entries) { theme ->
                ListItem(
                    title =
                        stringResource(
                            id =
                                context.resources.getIdentifier(
                                    theme.type,
                                    "string",
                                    context.packageName,
                                ),
                        ),
                    onClick = {
                        settingsViewModel.updateSettingsState(
                            state = settingsState.copy(theme = theme),
                        )
                    },
                    firstItem = {
                        RadioButton(
                            selected = theme == settingsState.theme,
                            onClick = {
                                settingsViewModel.updateSettingsState(
                                    state = settingsState.copy(
                                        theme = theme,
                                    ),
                                )
                            },
                        )
                    },
                )
            }

            item {
                HorizontalDivider()
            }

            item {
                ListItem(
                    title = stringResource(id = R.string.appearance_theme_monochrome),
                    onClick = {
                        settingsViewModel.updateSettingsState(
                            state = settingsState.copy(
                                monochrome = !settingsState.monochrome
                            )
                        )
                    },
                    lastItem = {
                        Switch(
                            checked = settingsState.monochrome,
                            onCheckedChange = { monochrome ->
                                settingsViewModel.updateSettingsState(
                                    state = settingsState.copy(
                                        monochrome = monochrome
                                    )
                                )
                            })
                    })
            }
        }
    }
}