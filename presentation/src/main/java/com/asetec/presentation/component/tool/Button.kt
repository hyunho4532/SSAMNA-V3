package com.asetec.presentation.component.tool

import android.content.Context
import android.os.Build
import android.os.Process
import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.domain.model.location.Coordinate
import com.asetec.domain.model.state.Challenge
import com.asetec.presentation.R
import com.asetec.presentation.component.util.responsive.setUpButtonWidth
import com.asetec.presentation.enum.ButtonType
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import com.asetec.presentation.viewmodel.ChallengeViewModel
import com.asetec.presentation.viewmodel.LocationManagerViewModel
import com.asetec.presentation.viewmodel.SensorManagerViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun CustomButton(
    type: ButtonType,
    width: Dp,
    height: Dp,
    text: String,
    showIcon: Boolean = false,
    backgroundColor: Color,
    onNavigateToLogin: () -> Unit = {},
    shape: String = "Circle",
    data: Challenge = Challenge(),
    onClick: (permissionPopup: Boolean) -> Unit = { },
    @ApplicationContext context: Context = LocalContext.current,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    coordinate: List<LatLng> = emptyList(),
    locationManagerViewModel: LocationManagerViewModel = hiltViewModel(),
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel(),
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    challengeViewModel: ChallengeViewModel = hiltViewModel()
) {

    val activates = activityLocationViewModel.activates.collectAsState()

    Button(
        onClick = {
            when (type) {
                ButtonType.PermissionStatus.POPUP -> {
                    onClick(true)
                }
                ButtonType.PermissionStatus.CANCEL -> {
                    Process.killProcess(Process.myPid())
                }
                ButtonType.PermissionStatus.CLICK -> {
                    onNavigateToLogin()
                }
                ButtonType.MarkerStatus.FINISH -> {
                    activityLocationViewModel.setLatLng(
                        latitude = cameraPositionState.position.target.latitude,
                        longitude = cameraPositionState.position.target.longitude
                    )

                }
                else -> {
                    when (type) {
                        ButtonType.RunningStatus.FINISH -> {
                            if (sensorManagerViewModel.getSavedSensorState() > 100) {
                                sensorManagerViewModel.stopService(
                                    runningStatus = true,
                                    isRunning = false
                                )
                                locationManagerViewModel.stopService()
                                sensorManagerViewModel.stopWatch()
                            } else {
                                Toast.makeText(context, "최소 100보 이상은 걸어야 합니다!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        ButtonType.RunningStatus.InsertStatus.RUNNING -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                activityLocationViewModel.saveActivity(
                                    runningIcon = activates.value.activateResId,
                                    runningTitle = activates.value.activateName,
                                    coordinate = coordinate
                                )
                            }
                        }

                        ButtonType.RunningStatus.InsertStatus.CHALLENGE -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                challengeViewModel.saveChallenge(data)
                            }
                        }

                        else -> {
                            locationManagerViewModel.startService()
                            sensorManagerViewModel.startService(true)
                            sensorManagerViewModel.startWatch()
                        }
                    }
                }
            }
        },
        modifier = Modifier
            .wrapContentSize()
            .width(setUpButtonWidth(cardWidth = width))
            .height(height),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = if (shape == "Circle") CircleShape else RectangleShape
    ) {
        if (showIcon) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_water_drop_24),
                contentDescription = "운동 여정하기!"
            )

            Spacer(width = 8.dp, height = 0.dp)
        }

        Text(text = text)
    }
}