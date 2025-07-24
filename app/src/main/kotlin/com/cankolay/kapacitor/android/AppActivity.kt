package com.cankolay.kapacitor.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import com.cankolay.kapacitor.android.presentation.AppUI
import com.cankolay.kapacitor.android.presentation.viewmodel.AppViewModel
import com.cankolay.kapacitor.android.presentation.viewmodel.user.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    private val appViewModel by viewModels<AppViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                appViewModel.isLoading.value || profileViewModel.isLoading.value
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
            AppUI()
        }
    }
}