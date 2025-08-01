package com.cankolay.kapacitor.android.presentation.view.settings.about

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.cankolay.kapacitor.android.presentation.R
import com.cankolay.kapacitor.android.presentation.composable.Card
import com.cankolay.kapacitor.android.presentation.composable.Icon
import com.cankolay.kapacitor.android.presentation.composable.ListItem
import com.cankolay.kapacitor.android.presentation.composition.LocalNavController
import com.cankolay.kapacitor.android.presentation.navigation.aboutRoutes
import com.cankolay.kapacitor.android.presentation.navigation.routeInfos

@Composable
fun AboutView() {
    val navController = LocalNavController.current

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            item {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                ) {
                    AppCard()

                    DevCard()
                }
            }

            items(items = aboutRoutes) { route ->
                val routeInfo = routeInfos[route]!!
                ListItem(
                    title = stringResource(id = routeInfo.title),
                    description = stringResource(id = routeInfo.description),
                    onClick = { navController.navigate(route = route) },
                    firstItem = {
                        Icon(
                            icon = routeInfo.icon,
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun AppCard() {
    val context = LocalContext.current

    val activityLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) {}

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = context.packageManager.getApplicationIcon(context.packageName),
                contentDescription = null,
                modifier =
                    Modifier
                        .clip(shape = CircleShape)
                        .size(size = 64.dp)
                        .padding(end = 16.dp),
            )

            Column {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text =
                        context.packageManager
                            .getPackageInfo(
                                context.packageName,
                                0,
                            )?.versionName ?: "1.0.0",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        HorizontalDivider()

        val appGithubUrl = stringResource(id = R.string.app_github_url)
        ListItem(
            title = stringResource(id = R.string.about_view_on, "Github"),
            description =
                stringResource(
                    id = R.string.about_view_on_desc,
                    "Github",
                ),
            onClick = {
                activityLauncher.launch(Intent(Intent.ACTION_VIEW, appGithubUrl.toUri()))
            },
            firstItem = {
                Icon(icon = painterResource(id = R.drawable.ic_github))
            },
        )
    }
}

@Composable
fun DevCard() {
    val activityLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) {}

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.dev),
                style = MaterialTheme.typography.titleLarge,
            )
        }

        HorizontalDivider()

        val devWebsiteUrl = stringResource(id = R.string.dev_website_url)
        ListItem(
            title = stringResource(id = R.string.dev_name),
            description = "@" + stringResource(id = R.string.dev_username),
            onClick = {
                activityLauncher.launch(Intent(Intent.ACTION_VIEW, devWebsiteUrl.toUri()))
            },
            firstItem = {
                Icon(icon = Icons.Default.Person)
            },
        )

        val devGithubUrl = stringResource(id = R.string.dev_github_url)
        ListItem(
            title = stringResource(id = R.string.about_follow_on, "Github"),
            description =
                stringResource(
                    id = R.string.about_follow_on_desc,
                    "Github",
                ),
            onClick = {
                activityLauncher.launch(Intent(Intent.ACTION_VIEW, devGithubUrl.toUri()))
            },
            firstItem = {
                Icon(icon = painterResource(id = R.drawable.ic_github))
            },
        )
    }
}