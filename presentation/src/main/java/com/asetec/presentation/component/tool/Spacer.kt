package com.asetec.presentation.component.tool

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.asetec.presentation.component.util.bottomBorder
import com.asetec.presentation.component.util.bottomBorderConditional

/** 공백을 주기 위한 함수 **/
@Composable
fun Spacer(
    width: Dp,
    height: Dp?,
    isBottomBorder: Boolean = false
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height!!)
            .bottomBorderConditional(isBottomBorder) {
                bottomBorder(1.dp, Color.Gray)
            }
    )
}