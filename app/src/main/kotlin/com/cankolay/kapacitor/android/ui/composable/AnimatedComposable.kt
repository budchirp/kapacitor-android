package com.cankolay.kapacitor.android.ui.composable

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cankolay.kapacitor.android.ui.motion.slideIn
import com.cankolay.kapacitor.android.ui.motion.slideOut
import com.cankolay.kapacitor.android.ui.navigation.Route
import com.cankolay.kapacitor.android.ui.navigation.allRoutes
import com.cankolay.kapacitor.android.ui.navigation.findByDestination

const val initialOffset: Float = 0.10f

inline fun <reified T : Any> NavGraphBuilder.animatedComposable(
    route: Route,
    crossinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = { slideIn(initialOffsetX = { (it * initialOffset).toInt() }) },
        exitTransition = { slideOut(targetOffsetX = { -(it * initialOffset).toInt() }) },
        popEnterTransition = { slideIn(initialOffsetX = { -(it * initialOffset).toInt() }) },
        popExitTransition = { slideOut(targetOffsetX = { (it * initialOffset).toInt() }) }
    ) {
        Layout(
            route = allRoutes.findByDestination(id = route.id),
        ) {
            content(this, it)
        }
    }
}