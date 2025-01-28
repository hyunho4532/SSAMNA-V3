package com.asetec.presentation.ui.main.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface {
                CompositionLocalProvider(
                    value = LocalDensity provides Density(
                        density = LocalDensity.current.density,
                        fontScale = 1f
                    ),
                ) {
                    RootScreen()
                }
            }
        }
    }
}