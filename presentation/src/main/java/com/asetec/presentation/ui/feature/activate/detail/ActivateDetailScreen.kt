package com.asetec.presentation.ui.feature.activate.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.asetec.domain.model.location.Coordinate
import com.asetec.presentation.R
import com.asetec.presentation.component.marker.MapMarker
import com.asetec.presentation.component.tool.CustomButton
import com.asetec.presentation.component.tool.Spacer
import com.asetec.presentation.component.tool.activateHistoryCard
import com.asetec.presentation.component.tool.chartDetailCard
import com.asetec.presentation.component.util.analyzeRunningFeedback
import com.asetec.presentation.component.util.responsive.setUpWidth
import com.asetec.presentation.enum.ButtonType
import com.asetec.presentation.ui.feature.analyze.FastRunning
import com.asetec.presentation.ui.feature.analyze.ModerateRunning
import com.asetec.presentation.ui.feature.analyze.OptimalRunning
import com.asetec.presentation.ui.feature.analyze.SlowRunning
import com.asetec.presentation.ui.feature.analyze.UnknownRunning
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.double

@Composable
fun ActivateDetailScreen(
    id: String,
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
) {
    val activateData = activityLocationViewModel.activateData.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current

    val pace = remember {
        mutableDoubleStateOf(0.0)
    }

    LaunchedEffect(key1 = Unit) {
        activityLocationViewModel.selectActivityFindById(id.toInt())
    }

    val coordsList: List<Coordinate> = activityLocationViewModel.setCoordList(activateData)

    if (coordsList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                cameraPositionState.move(
                    CameraUpdateFactory.newLatLngZoom(LatLng(coordsList.get(0).latitude, coordsList.get(0).longitude), 18f)
                )

                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(
                            top = 24.dp,
                            start = 24.dp,
                            end = 24.dp
                        ),
                    cameraPositionState = cameraPositionState
                ) {
                    coordsList.forEach { data ->
                        MapMarker(
                            context = context,
                            position = LatLng(data.latitude, data.longitude),
                            title = "활동 내역",
                            iconResourceId = R.drawable.location_marker
                        )
                    }
                }
            }

            /**
             * 운동 내역을 확인하기 위한 카드 UI
             * 시간, 칼로리, km 확인
             */
            activateHistoryCard(
                activate = activateData,
                height = 50.dp
            )

            Spacer(width = 0.dp, height = 40.dp)

            /**
             * 차트 상세 분석으로 이동하기 위한 카드 UI
             */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .align(Alignment.Start)
                    .padding(
                        top = 40.dp,
                        start = 24.dp
                    )
            ) {
                Column {
                    Text(
                        text = "차트 분석",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(
                        width = setUpWidth(),
                        height = 10.dp,
                        isBottomBorder = true
                    )

                    Box(
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        chartDetailCard(
                            height = 50.dp,
                            backgroundColor = Color.White,
                            navController = navController,
                            coordsList = coordsList
                        )
                    }
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .align(Alignment.Start)
                    .padding(
                        top = 40.dp,
                        start = 24.dp
                    )
            ) {
                Column {
                    Text(
                        text = "이번 활동의 페이스 분석",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(
                        width = setUpWidth(),
                        height = 10.dp,
                        isBottomBorder = true
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp)
                    ) {
                        Column {
                            Text(
                                text = analyzeRunningFeedback(
                                    activateData.value[0].time,
                                    (activateData.value[0].cul["km_cul"] as? JsonPrimitive)!!.double,
                                    (activateData.value[0].cul["kcal_cul"] as? JsonPrimitive)!!.double
                                ) {
                                  pace.doubleValue = it
                                },
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Start)
                    .padding(
                        top = 40.dp,
                        start = 24.dp
                    )
            ) {
                when (pace.doubleValue) {
                    in 0.1 .. 5.0 -> {
                        FastRunning()
                    }
                    in 5.0..7.0 -> {
                        ModerateRunning()
                    }
                    in 7.0..10.0 -> {
                        ModerateRunning()
                    }
                    in 10.0..12.0 -> {
                        OptimalRunning()
                    }
                    0.0 -> {
                        UnknownRunning()
                    }
                    else -> {
                        SlowRunning()
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 12.dp)
            ) {
                CustomButton(
                    type = ButtonType.RunningStatus.DeleteStatus.RUNNING,
                    width = setUpWidth(),
                    height = 40.dp,
                    text = "활동 내역 삭제",
                    backgroundColor = Color(0xFFEE3A3A),
                    activateData = activateData
                )
            }
        }
    }
}