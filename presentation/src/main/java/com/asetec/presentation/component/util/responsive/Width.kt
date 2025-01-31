package com.asetec.presentation.component.util.responsive

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

fun setUpCardWidth(densityDpi: Int): Dp = when {
    densityDpi <= 160 -> 240.dp
    densityDpi <= 240 -> 280.dp
    densityDpi <= 320 -> 320.dp
    densityDpi <= 480 -> 320.dp
    densityDpi <= 640 -> 340.dp
    else -> 340.dp
}

fun setUpButtonWidth(densityDpi: Int): Dp = when {
    densityDpi <= 160 -> 220.dp
    densityDpi <= 240 -> 240.dp
    densityDpi <= 320 -> 300.dp
    densityDpi <= 480 -> 320.dp
    densityDpi <= 640 -> 360.dp
    else -> 360.dp
}