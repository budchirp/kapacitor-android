package com.cankolay.kapacitor.android.presentation.composable

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.cankolay.kapacitor.android.presentation.composable.layout.Layout
import com.cankolay.kapacitor.android.presentation.motion.slideIn
import com.cankolay.kapacitor.android.presentation.motion.slideOut
import com.cankolay.kapacitor.android.presentation.navigation.Route

const val initialOffset: Float = 0.10f

inline fun <reified T : Any> NavGraphBuilder.animatedComposable(
    defaultLayout: Boolean = true,
    crossinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = { slideIn(initialOffsetX = { (it * initialOffset).toInt() }) },
        exitTransition = { slideOut(targetOffsetX = { -(it * initialOffset).toInt() }) },
        popEnterTransition = { slideIn(initialOffsetX = { -(it * initialOffset).toInt() }) },
        popExitTransition = { slideOut(targetOffsetX = { (it * initialOffset).toInt() }) }
    ) { backStackEntry ->
        if (defaultLayout) {
            Layout(
                route = backStackEntry.toRoute<T>() as Route
            ) {
                content(this, backStackEntry)
            }
        } else {
            content(this, backStackEntry)
        }
    }
}