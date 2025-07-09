package com.cankolay.kapacitor.android.ui.view.settings.appearance

import android.os.Build
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cankolay.kapacitor.android.R
import com.cankolay.kapacitor.android.data.datastore.model.SettingsState
import com.cankolay.kapacitor.android.data.datastore.model.defaultSettingsState
import com.cankolay.kapacitor.android.ui.composable.Icon
import com.cankolay.kapacitor.android.ui.composable.ListItem
import com.cankolay.kapacitor.android.ui.navigation.materialYouView
import com.cankolay.kapacitor.android.ui.navigation.routeInfos
import com.cankolay.kapacitor.android.ui.theme.toColor
import com.cankolay.kapacitor.android.ui.util.UiUtil
import com.cankolay.kapacitor.android.viewmodel.settings.SettingsViewModel
import com.kyant.m3color.hct.Hct
import com.kyant.m3color.scheme.SchemeTonalSpot

private val colorList =
    ((0..15)).map { it * 22.5 }.map { Color(color = Hct.from(it, 40.0, 40.0).toInt()) }

@Composable
fun MaterialYouView(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val settingsState by settingsViewModel.settingsStateFlow.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        ) {
            item {
                MaterialYouToggle(
                    settingsViewModel = settingsViewModel,
                    settingsState = settingsState
                )
            }

            item {
                ColorButtons(
                    settingsViewModel = settingsViewModel,
                    settingsState = settingsState
                )
            }

            item {
                val isAndroid12Available = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                ListItem(
                    title =
                        stringResource(
                            id = R.string.appearance_material_you_wallpaper_colors
                        ),
                    description =
                        stringResource(
                            id =
                                if (isAndroid12Available)
                                    R.string
                                        .appearance_material_you_wallpaper_colors
                                else
                                    R.string
                                        .appearance_material_you_wallpaper_colors_unsupported
                        ),
                    onClick = {
                        settingsViewModel.updateSettingsState(
                            state =
                                settingsState.copy(
                                    materialYou = true,
                                    keyColor =
                                        if (settingsState.wallpaperColors)
                                            defaultSettingsState().keyColor
                                        else colorList.first(),
                                    wallpaperColors = !settingsState.wallpaperColors,
                                    monochrome = false
                                ),
                        )
                    },
                    lastItem = {
                        Switch(
                            checked = settingsState.wallpaperColors,
                            onCheckedChange = { wallpaperColors ->
                                settingsViewModel.updateSettingsState(
                                    state =
                                        settingsState.copy(
                                            materialYou = true,
                                            keyColor =
                                                if (wallpaperColors)
                                                    defaultSettingsState()
                                                        .keyColor
                                                else colorList.first(),
                                            wallpaperColors = wallpaperColors
                                        ),
                                )
                            },
                            enabled = isAndroid12Available
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun MaterialYouToggle(settingsViewModel: SettingsViewModel, settingsState: SettingsState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clip(shape = MaterialTheme.shapes.extraLarge)
                    .background(
                        color = colorScheme.surfaceContainer,
                    )
                    .toggleable(
                        value = settingsState.materialYou,
                    ) { materialYou ->
                        settingsViewModel.updateSettingsState(
                            state =
                                settingsState.copy(
                                    materialYou = materialYou,
                                    keyColor =
                                        if (materialYou)
                                            colorList.first()
                                        else
                                            defaultSettingsState()
                                                .keyColor,
                                    wallpaperColors = false
                                ),
                        )
                    }
                    .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            Text(
                text = stringResource(id = routeInfos[materialYouView]!!.title),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 1f),
                style = MaterialTheme.typography.titleMedium,
            )

            Switch(
                checked = settingsState.materialYou,
                onCheckedChange = { materialYou ->
                    settingsViewModel.updateSettingsState(
                        state =
                            settingsState.copy(
                                materialYou = materialYou,
                                keyColor =
                                    if (materialYou) colorList.first()
                                    else defaultSettingsState().keyColor,
                                wallpaperColors = false
                            ),
                    )
                },
            )
        }
    }
}

@Composable
fun ColorButtons(settingsViewModel: SettingsViewModel, settingsState: SettingsState) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        val pagerState = rememberPagerState(pageCount = { colorList.size / 4 })
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page: Int ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
            ) {
                colorList.subList(fromIndex = page * 4, toIndex = (page + 1) * 4)
                    .forEach { color: Color ->
                        ColorButton(
                            settingsViewModel = settingsViewModel,
                            settingsState = settingsState,
                            color = color
                        )
                    }
            }
        }

        HorizontalPagerPageIndicator(pagerState = pagerState)
    }
}

@Composable
private fun RowScope.ColorButton(
    settingsViewModel: SettingsViewModel,
    settingsState: SettingsState,
    color: Color
) {
    Surface(
        modifier = Modifier
            .weight(weight = 1f, fill = false)
            .aspectRatio(ratio = 1f),
        shape = RoundedCornerShape(size = 24.dp),
        color = colorScheme.surfaceContainer,
        onClick = {
            settingsViewModel.updateSettingsState(
                state =
                    settingsState.copy(
                        materialYou = true,
                        keyColor = color,
                        wallpaperColors = false,
                        monochrome = false,
                    )
            )
        }
    ) {
        val hct = Hct.fromInt(color.toArgb())
        val scheme = SchemeTonalSpot(hct, UiUtil.isDark(settingsState = settingsState), 0.0)

        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier =
                    Modifier
                        .padding(all = 16.dp)
                        .size(size = 48.dp)
                        .clip(shape = CircleShape)
                        .align(alignment = Alignment.Center)
                        .drawBehind { drawCircle(color = color) },
            ) {
                Surface(
                    color = scheme.primary.toColor(),
                    modifier =
                        Modifier
                            .align(alignment = Alignment.BottomStart)
                            .size(size = 25.dp)
                ) {}
                Surface(
                    color = scheme.onPrimary.toColor(),
                    modifier =
                        Modifier
                            .align(alignment = Alignment.BottomEnd)
                            .size(size = 25.dp)
                ) {}

                val isSelected = { settingsState.keyColor == color }

                val animatedAlpha by
                animateFloatAsState(targetValue = if (isSelected.invoke()) 1f else 0f)

                val containerColor = colorScheme.primaryContainer
                val animatedContainerSize by
                animateDpAsState(targetValue = if (isSelected.invoke()) 32.dp else 0.dp)

                val animatedIconSize by
                animateDpAsState(targetValue = if (isSelected.invoke()) 24.dp else 0.dp)

                Box(
                    modifier =
                        Modifier
                            .graphicsLayer { alpha = animatedAlpha }
                            .padding(all = 8.dp)
                            .size(size = animatedContainerSize)
                            .clip(shape = CircleShape)
                            .drawBehind { drawCircle(color = containerColor) }
                            .padding(all = 4.dp)
                            .align(alignment = Alignment.Center),
                ) {
                    Icon(
                        icon = Icons.Outlined.Check,
                        modifier =
                            Modifier
                                .graphicsLayer { alpha = animatedAlpha }
                                .size(size = animatedIconSize)
                                .align(Alignment.Center),
                    )
                }
            }
        }
    }
}

@Composable
private fun HorizontalPagerPageIndicator(pagerState: PagerState) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        repeat(times = pagerState.pageCount) { page ->
            Box(
                modifier =
                    Modifier
                        .padding(horizontal = 2.dp)
                        .clip(shape = CircleShape)
                        .background(
                            color =
                                if (pagerState.currentPage == page)
                                    colorScheme.primary
                                else colorScheme.surfaceContainer
                        )
                        .size(size = 8.dp)
            )
        }
    }
}
