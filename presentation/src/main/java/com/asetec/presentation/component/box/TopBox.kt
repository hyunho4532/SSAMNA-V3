package com.asetec.presentation.component.box

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.presentation.R
import com.asetec.presentation.component.icon.Footprint
import com.asetec.presentation.component.tool.customButton
import com.asetec.presentation.component.tool.Spacer
import com.asetec.presentation.component.util.FormatImpl
import com.asetec.presentation.enum.ButtonType
import com.asetec.presentation.viewmodel.SensorManagerViewModel

/**
 * 구글 지도에서 맨 위에 측정 중인 상태에서 걸음 수를 보여준다.
 */
@SuppressLint("NewApi")
@Composable
fun TopBox(
    context: Context,
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel()
): Boolean {

    val activates = sensorManagerViewModel.activates.collectAsState()

    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    var isSensorListenerRegistered by remember {
        mutableStateOf(false)
    }

    var isShowRunning by remember {
        mutableStateOf(false)
    }

    val listener = remember {
        sensorManagerViewModel.sensorEventListener()
    }

    val stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

    DisposableEffect(Unit) {
        stepDetector?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI)
            sensorManagerViewModel.startWatch()
            isSensorListenerRegistered = true
        }

        onDispose {
            sensorManager.unregisterListener(listener)
            sensorManagerViewModel.setSavedSensorState()
            isSensorListenerRegistered = false
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
                        text = "${sensorManagerViewModel.getSavedSensorState()}",
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
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "음악", fontWeight = FontWeight.Bold)

                        Spacer(width = 2.dp, height = 0.dp)

                        Icon(
                            painter = painterResource(id = R.drawable.baseline_music_24),
                            contentDescription = "음악 아이콘",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Text(
                        text = "선택!",
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .align(Alignment.BottomCenter)
                            .clickable {
                                val intent = Intent(Intent.ACTION_MAIN)
                                intent.setPackage("com.google.android.apps.youtube.music")
                                intent.addCategory(Intent.CATEGORY_LAUNCHER)

                                context.startActivity(intent)
                            }
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
                        horizontalArrangement = Arrangement.End
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
                isShowRunning = customButton(
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

    return isShowRunning
}