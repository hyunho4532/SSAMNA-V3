package com.asetec.presentation.component.box

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.presentation.R
import com.asetec.presentation.component.icon.Footprint
import com.asetec.presentation.component.icon.TimePrint
import com.asetec.presentation.component.tool.CustomButton
import com.asetec.presentation.component.tool.Spacer
import com.asetec.presentation.component.util.FormatImpl
import com.asetec.presentation.enum.ButtonType
import com.asetec.presentation.viewmodel.SensorManagerViewModel

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
            sensorManagerViewModel.setSavedSensorState()
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
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .height(46.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .height(24.dp)
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "걸음", fontWeight = FontWeight.Bold)

                        Spacer(width = 2.dp, height = 0.dp)

                        Icon(
                            imageVector = Footprint,
                            contentDescription = "걸음 아이콘",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Text(
                        text = "${activates.value.pedometerCount}",
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .height(46.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .height(24.dp)
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "음악", fontWeight = FontWeight.Bold)

                        Spacer(width = 2.dp, height = 0.dp)

                        Icon(
                            painter = painterResource(id = R.drawable.baseline_music_24),
                            contentDescription = "음악 아이콘",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(
                            text = "0",
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .align(Alignment.BottomCenter)
                        )
                    }

                }
            }

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .height(46.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .height(24.dp)
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "시간", fontWeight = FontWeight.Bold)

                        Spacer(width = 2.dp, height = 0.dp)

                        Icon(
                            imageVector = Footprint,
                            contentDescription = "시간 아이콘",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Text(
                        text = FormatImpl("YY:MM:DD:H").getFormatTime(activates.value.time),
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                CustomButton(
                    type = ButtonType.RunningStatus.FINISH,
                    width = 110.dp,
                    height = 32.dp,
                    text = "측정 완료!",
                    backgroundColor = Color(0xFF5c9afa),
                    context = context,
                    shape = "Circle"
                )
            }
        }
    }
}