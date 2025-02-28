package com.asetec.presentation.ui.feature.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivateDetailActivity : ComponentActivity() {

    private val activityLocationViewModel: ActivityLocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this.applicationContext

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val googleId = intent.getStringExtra("googleId")
                val date = intent.getStringExtra("date")

                DetailScreen(
                    context = context,
                    activityLocationViewModel = activityLocationViewModel,
                    googleId = googleId!!,
                    date = date!!
                )
            }
        }
    }
}