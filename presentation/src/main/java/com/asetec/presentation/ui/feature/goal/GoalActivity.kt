package com.asetec.presentation.ui.feature.goal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoalActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                GoalScreen()
            }
        }
    }
}