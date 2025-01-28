package com.asetec.presentation.component.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.domain.model.state.Challenge
import com.asetec.presentation.R
import com.asetec.presentation.component.row.BoxRow
import com.asetec.presentation.component.tool.CustomButton
import com.asetec.presentation.component.tool.Spacer
import com.asetec.presentation.enum.ButtonType
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import com.asetec.presentation.viewmodel.JsonParseViewModel
import com.asetec.presentation.viewmodel.SensorManagerViewModel
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ShowCompleteDialog(
    context: Context,
    sensorManagerViewModel: SensorManagerViewModel,
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    jsonParseViewModel: JsonParseViewModel = hiltViewModel()
) {
    val cameraPositionState = rememberCameraPositionState()

    val activates = activityLocationViewModel.activates.collectAsState()

    LaunchedEffect(key1 = Unit) {
        if (jsonParseViewModel.runningJsonData.isEmpty()) {
            jsonParseViewModel.activateJsonParse("running.json", "running")
        }
    }

    Dialog(
        onDismissRequest = {
            sensorManagerViewModel.stopService(
                context = context,
                runningStatus = false,
                isRunning = false
            )
        }
    ) {
        Card(
            modifier = Modifier
                .width(420.dp)
                .height(560.dp)
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 8.dp
                    )
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = "1. 회원님, 이번 운동은 어떠셨나요?",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                BoxRow(
                    context = context,
                    data = jsonParseViewModel.runningJsonData
                )

                Box(
                    modifier = Modifier
                        .padding(top = 48.dp)
                ) {
                    Text(
                        text = "2. 회원님, 제목을 정해주세요!",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(top = 12.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .width(280.dp)
                            .height(56.dp),
                        value = activates.value.runningTitle,
                        onValueChange = {
                            activityLocationViewModel.updateRunningTitle(it)
                        },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 14.sp),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.hint_name_exercise),
                                color = Color.Gray
                            )
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(top = 48.dp)
                ) {
                    Text(
                        text = "3. 회원님이 운동한 내역",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                GoogleMap(
                    modifier = Modifier
                        .width(300.dp)
                        .height(420.dp)
                        .padding(top = 12.dp),
                    cameraPositionState = cameraPositionState
                ) {

                }

                Box(
                    modifier = Modifier
                        .padding(top = 8.dp, end = 4.dp)
                ) {
                    CustomButton(
                        type = ButtonType.RunningStatus.InsertStatus.RUNNING,
                        width = 300.dp,
                        height = 32.dp,
                        text = "활동 저장!",
                        showIcon = false,
                        backgroundColor = Color(0xFF5c9afa),
                        navController = null,
                        context = context,
                        shape = "Rectangle"
                    )
                }

                Spacer(width = 0.dp, height = 16.dp)
            }
        }
    }
}

@Composable
fun ShowChallengeDialog(
    index: MutableState<Int>,
    challenge: List<Challenge>,
    isChallengeIsPopup: MutableState<Boolean>
) {

    val challengeData = challenge[index.value]

    Dialog(
        onDismissRequest = {
            isChallengeIsPopup.value = false
        }
    ) {
        Card(
            modifier = Modifier
                .width(420.dp)
                .height(200.dp)
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "챌린저 등록!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 26.dp, start = 12.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "등록하실 챌린지는? ${challengeData.name}",
                        fontSize = 14.sp,
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 12.dp, top = 4.dp)
                ) {
                    Text(
                        text = challengeData.description,
                        fontSize = 12.sp
                    )
                }

                Spacer(width = 0.dp, height = 24.dp)

                CustomButton(
                    type = ButtonType.RunningStatus.InsertStatus.CHALLENGE,
                    width = 240.dp,
                    height = 40.dp,
                    text = "챌린저 등록!",
                    showIcon = false,
                    backgroundColor = Color(0xFF5c9afa),
                    context = null,
                    shape = "Rectangle",
                    data = challengeData
                )
            }
        }
    }
}