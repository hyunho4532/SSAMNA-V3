package com.asetec.presentation.component.box.polygon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import com.asetec.presentation.enum.ProfileStatusType

/**
 * 다각형 박스
 */
@Composable
fun PolygonBox(
    title: String,
    activateCount: Int = 0,
    sumKcal: Double = 0.0,
    profileStatusType: ProfileStatusType
) {

    val hexagon = remember {
        RoundedPolygon(
            6,
            rounding = CornerRounding(0.2f)
        )
    }
    val clip = remember(hexagon) {
        RoundedPolygonShape(polygon = hexagon)
    }

    Box(
        modifier = Modifier
            .clip(clip)
            .background(Color(0xFF429bf5))
            .width(90.dp)
            .height(90.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                title,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Text(
                text = when (profileStatusType) {
                    ProfileStatusType.Activate -> activateCount.toString()
                    ProfileStatusType.Kcal -> sumKcal.toString()
                    else -> "0"
                },
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}