package com.asetec.presentation.ui.main.home.screen


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.domain.model.user.User
import com.asetec.presentation.component.aside.HomeAside
import com.asetec.presentation.component.box.TopBox
import com.asetec.presentation.component.dialog.ShowCompleteDialog
import com.asetec.presentation.component.tool.CircularProgress
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import com.asetec.presentation.viewmodel.SensorManagerViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UseOfNonLambdaOffsetOverload", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    fusedLocationClient: FusedLocationProviderClient,
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel(),
    context: Context,
    userList: State<User>
) {
    val locationState = activityLocationViewModel.locations.collectAsState()
    val activates by sensorManagerViewModel.activates.collectAsState()

    var isLocationLoaded by remember {
        mutableStateOf(false)
    }

    val locationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        )
    } else {
        TODO("VERSION.SDK_INT < Q")
    }

    var isPanelVisible by remember {
        mutableStateOf(false)
    }

    val panelWidth by animateDpAsState(targetValue = if (isPanelVisible) 10.dp else 0.dp,
        label = ""
    )

    val buttonOffset by animateDpAsState(targetValue = if (isPanelVisible) (-130).dp else 0.dp,
        label = ""
    )

    LaunchedEffect (key1 = Unit) {
        locationPermissionState.launchMultiplePermissionRequest()
    }

    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            activityLocationViewModel.getCurrentLocation(fusedLocationClient) {
                isLocationLoaded = true
            }
        }
    }

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(locationState.value.latitude, locationState.value.latitude) {
        if (isLocationLoaded) {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(LatLng(locationState.value.latitude, locationState.value.longitude), 12f)
            )
        }
    }

    if (locationPermissionState.allPermissionsGranted) {
        if (isLocationLoaded) {
            GoogleMap(
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = LatLng(locationState.value.latitude, locationState.value.longitude)),
                    title = "서울",
                    snippet = "한국의 수도"
                )
            }

            if (activates.activateButtonName == "측정 중!" || sensorManagerViewModel.getSavedIsRunningState()) {
                TopBox(context)
            }

            if (activates.showRunningStatus) {
                ShowCompleteDialog(
                    context = context,
                    sensorManagerViewModel
                )
            }

            Box (
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = { isPanelVisible = !isPanelVisible },
                    modifier = Modifier
                        .width(58.dp)
                        .height(50.dp)
                        .offset(x = buttonOffset)
                        .align(Alignment.CenterEnd),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RectangleShape
                ) {
                    Text(
                        text = if (isPanelVisible) "→" else "←",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (isPanelVisible) {
                    Box(
                        modifier = Modifier
                            .width(140.dp)
                            .height(230.dp)
                            .offset(x = panelWidth)
                            .align(Alignment.CenterEnd)
                            .background(Color.White)
                            .animateContentSize()
                    ) {
                        HomeAside(
                            context = context,
                            userList = userList
                        )
                    }
                }
            }

        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgress(text = "현재 위치를 불러오고 있습니다!")
            }
            
        }
    }
}