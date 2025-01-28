package com.asetec.presentation.component.tool

import android.content.Context
import android.os.Build
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.asetec.domain.model.state.Challenge
import com.asetec.presentation.R
import com.asetec.presentation.enum.ButtonType
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import com.asetec.presentation.viewmodel.ChallengeViewModel
import com.asetec.presentation.viewmodel.SensorManagerViewModel

@Composable
fun CustomButton(
    type: ButtonType,
    width: Dp,
    height: Dp,
    text: String,
    showIcon: Boolean,
    backgroundColor: Color,
    navController: NavController? = rememberNavController(),
    context: Context?,
    shape: String = "Circle",
    data: Challenge = Challenge(),
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel(),
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    challengeViewModel: ChallengeViewModel = hiltViewModel()
) {
    Button(
        onClick = {
            if (type == ButtonType.ROUTER) {
                navController?.navigate("login") {
                    popUpTo("splash") {
                        inclusive = true
                    }
                }
            } else {
                when (type) {
                    ButtonType.RunningStatus.FINISH -> {
                        if (sensorManagerViewModel.getSavedSensorState() > 100) {
                            sensorManagerViewModel.stopService(
                                context = context!!,
                                runningStatus = true,
                                isRunning = false
                            )
                            sensorManagerViewModel.stopWatch()
                        } else {
                            Toast.makeText(context, "최소 100보 이상은 걸어야 합니다!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    ButtonType.RunningStatus.InsertStatus.RUNNING -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            activityLocationViewModel.saveActivity()
                        }
                    }
                    ButtonType.RunningStatus.InsertStatus.CHALLENGE -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            challengeViewModel.saveChallenge(data)
                        }
                    }
                    else -> {
                        sensorManagerViewModel.startService(context!!, true)
                        sensorManagerViewModel.startWatch()
                    }
                }
            }
        },
        modifier = Modifier
            .wrapContentSize()
            .width(width)
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