package com.asetec.presentation.component.box

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.presentation.component.icon.Footprint
import com.asetec.presentation.component.icon.TimePrint
import com.asetec.presentation.component.tool.CustomButton
import com.asetec.presentation.component.tool.Spacer
import com.asetec.presentation.enum.ButtonType
import com.asetec.presentation.ui.util.formatTime
import com.asetec.presentation.viewmodel.SensorManagerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * 구글 지도에서 맨 위에 측정 중인 상태에서 걸음 수를 보여준다.
 */
@Composable
fun TopBox(
    context: Context,
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel()
) {

    val activates = sensorManagerViewModel.activates.collectAsState()

    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    LaunchedEffect(key1 = Unit) {
        sensorManagerViewModel.getSavedSensorState()
        sensorManagerViewModel.getSavedTimeState()
    }

    DisposableEffect(Unit) {
        val listener = sensorManagerViewModel.sensorEventListener()

        stepDetector?.let {
            sensorManager.unregisterListener(listener)
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI)
            sensorManagerViewModel.startWatch()
        }

        onDispose {
            sensorManager.unregisterListener(listener)
            sensorManagerViewModel.setSavedSensorState()
        }
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 12.dp)
    ) {
        Column (
            modifier = Modifier
                .width(100.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .height(46.dp)
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "만보기 걸음",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(width = 4.dp, height = 0.dp)

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Footprint,
                            contentDescription = "만보기 아이콘"
                        )
                    }
                }

                Text(
                    text = "${activates.value.pedometerCount} 걸음",
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }

        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(46.dp)
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "시간",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(width = 4.dp, height = 0.dp)

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = TimePrint,
                            contentDescription = "만보기 아이콘"
                        )
                    }
                }

                Text(
                    text = formatTime(activates.value.time),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 4.dp)
        ) {
            CustomButton(
                type = ButtonType.RunningStatus.FINISH,
                width = 110.dp,
                height = 32.dp,
                text = "측정 완료!",
                showIcon = false,
                backgroundColor = Color(0xFF5c9afa),
                navController = null,
                context = context,
                shape = "Circle"
            )
        }
    }
}