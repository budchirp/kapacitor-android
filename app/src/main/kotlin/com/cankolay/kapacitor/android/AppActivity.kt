package com.cankolay.kapacitor.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.rememberDrawerState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.cankolay.kapacitor.android.ui.composable.AppLayout
import com.cankolay.kapacitor.android.ui.composition.ProvideDrawerState
import com.cankolay.kapacitor.android.ui.composition.ProvideNavController
import com.cankolay.kapacitor.android.ui.navigation.AppNavGraph
import com.cankolay.kapacitor.android.ui.theme.AppTheme
import com.cankolay.kapacitor.android.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    private val appViewModel by viewModels<AppViewModel>()

    private var isLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            appViewModel.isLoading.collect {
                isLoading = it
            }
        }


        installSplashScreen().apply {
            setKeepOnScreenCondition {
                isLoading
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(
            window.decorView,
        ) { view, insets ->
            view.setPadding(0, 0, 0, 0)
            insets
        }

        enableEdgeToEdge()

        setContent {
            AppTheme {
                ProvideNavController(navController = rememberNavController()) {
                    ProvideDrawerState(drawerState = rememberDrawerState(initialValue = Closed)) {
                        AppLayout {
                            AppNavGraph()
                        }
                    }
                }
            }
        }
    }
}