package com.asetec.presentation.component.util.responsive

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun setUpSliderWidth(densityDpi: Int): Dp = when {
    densityDpi <= 160 -> 280.dp
    densityDpi <= 240 -> 300.dp
    densityDpi <= 320 -> 320.dp
    densityDpi <= 480 -> 340.dp
    densityDpi <= 640 -> 360.dp
    else -> 380.dp
}

/**
 * Jetpack compose에서 화면 크기와 관련된 값을 설정한다.
 */
@Composable
fun setUpWidth(): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    return screenWidth * 0.9f
}

fun setUpButtonWidth(densityDpi: Int): Dp = when {
    densityDpi <= 160 -> 220.dp
    densityDpi <= 240 -> 240.dp
    densityDpi <= 320 -> 300.dp
    densityDpi <= 480 -> 320.dp
    densityDpi <= 640 -> 360.dp
    else -> 360.dp
}