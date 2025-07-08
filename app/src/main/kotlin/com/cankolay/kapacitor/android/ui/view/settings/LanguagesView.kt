package com.cankolay.kapacitor.android.ui.view.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import com.cankolay.kapacitor.android.ui.composable.ListItem

@Composable
fun LanguagesView() {
    val context = LocalContext.current

    val languages = arrayOf("en", "tr")
    val currentLanguage =
        AppCompatDelegate.getApplicationLocales().toLanguageTags().ifEmpty { "en" }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(languages) { language ->
                ListItem(
                    title =
                        stringResource(
                            id =
                                context.resources.getIdentifier(
                                    language,
                                    "string",
                                    context.packageName,
                                ),
                        ),
                    onClick = {
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags(language),
                        )
                    },
                    firstItem = {
                        RadioButton(
                            selected =
                                currentLanguage.contains(
                                    other = language,
                                    ignoreCase = true,
                                ),
                            onClick = {
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.forLanguageTags(language),
                                )
                            },
                        )
                    },
                )
            }
        }
    }
}