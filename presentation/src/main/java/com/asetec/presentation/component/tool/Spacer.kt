package com.asetec.presentation.component.tool

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/** 공백을 주기 위한 함수 **/
@Composable
fun Spacer(width: Dp, height: Dp?) {
    Box(modifier = Modifier.width(width).height(height!!))
}