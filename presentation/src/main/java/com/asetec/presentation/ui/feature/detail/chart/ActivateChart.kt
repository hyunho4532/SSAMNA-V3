package com.asetec.presentation.ui.feature.detail.chart

import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asetec.domain.model.location.Coordinate
import com.asetec.presentation.component.util.calculatorActivateChartWidth

@Composable
fun ActivateChart(
    coordsList: List<Coordinate>
) {
    val altitudeList = remember(coordsList) {
        coordsList.map { it.longitude.toFloat() }
    }

    Column {
        Text(
            modifier = Modifier.padding(top = 12.dp, start = 12.dp),
            text = "고도 분석",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
        ) {
            Canvas(
                modifier = Modifier
                    .width(calculatorActivateChartWidth(coordsList = coordsList))
                    .height(90.dp)
                    .padding(12.dp)
            ) {
                val width = size.width
                val height = size.height
                val maxAltitude = altitudeList.maxOrNull() ?: 1f
                val minAltitude = altitudeList.minOrNull() ?: 0f
                val altitudeRange = maxAltitude - minAltitude

                altitudeList.forEachIndexed { index, altitude ->
                    if (index > 0) {
                        val startX = (index - 1) * (width / altitudeList.size)
                        val startY = height - ((altitudeList[index - 1] - minAltitude) / altitudeRange) * height
                        val endX = index * (width / altitudeList.size)
                        val endY = height - ((altitude - minAltitude) / altitudeRange) * height

                        drawLine(
                            color =  Color(0xFF5c9afa),
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = 5f
                        )

                        drawCircle(
                            color = Color(0xFF5c9afa),
                            radius = 8f,
                            center = Offset(endX, endY)
                        )

                        drawContext.canvas.nativeCanvas.apply {
                            val text = String.format("%.3f", altitude)

                            drawText(
                                text,
                                endX - 44,
                                endY + 40,
                                TextPaint().apply {
                                    textSize = 30f
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}