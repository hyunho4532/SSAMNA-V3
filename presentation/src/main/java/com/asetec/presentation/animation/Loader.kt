package com.asetec.presentation.animation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieRetrySignal
import com.asetec.presentation.R

@Composable
fun SplashLoader(resId: Int) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(resId)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = Int.MAX_VALUE
    )

    LottieAnimation(
        composition = composition,
        progress = { progress }
    )
}