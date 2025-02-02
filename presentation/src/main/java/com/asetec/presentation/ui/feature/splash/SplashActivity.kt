package com.asetec.presentation.ui.feature.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.asetec.presentation.ui.feature.splash.ui.theme.SSAMNATheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SSAMNATheme {

            }
        }
    }
}