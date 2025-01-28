package com.asetec.presentation.ui.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.asetec.presentation.R
import com.asetec.presentation.animation.SplashLoader
import com.asetec.presentation.component.tool.CircularProgress
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = Unit) {
        delay(3000)
        navController.navigate("home") {
            popUpTo("splash") {
                inclusive = true
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(480.dp)
        ) {
            SplashLoader(R.raw.splash)
        }

        CircularProgress(
            text = "운동의 여정을 불러오는 중"
        )
    }
}