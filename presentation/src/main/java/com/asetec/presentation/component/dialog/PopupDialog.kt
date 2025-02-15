package com.asetec.presentation.component.dialog

import PermiActivate
import PermiLocation
import PermiNotification
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.asetec.domain.model.state.Challenge
import com.asetec.presentation.R
import com.asetec.presentation.component.row.BoxRow
import com.asetec.presentation.component.tool.CustomButton
import com.asetec.presentation.component.tool.Spacer
import com.asetec.presentation.component.util.responsive.setUpDialogWidth
import com.asetec.presentation.component.util.responsive.setUpWidth
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
    isChallengePopup: MutableState<Boolean>
) {

    val challengeData = challenge[index.value]

    Dialog(
        onDismissRequest = {
            isChallengePopup.value = false
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
                    text = "챌린지 등록!",
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
                    text = "챌린지 등록!",
                    showIcon = false,
                    backgroundColor = Color(0xFF5c9afa),
                    shape = "Rectangle",
                    data = challengeData
                )
            }
        }
    }
}

@Composable
fun PermissionDialog(
    isPermissionPopup: MutableState<Boolean>,
    onNavigateToLogin: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            isPermissionPopup.value = false
        }
    ) {
        Card(
            modifier = Modifier
                .width(420.dp)
                .height(420.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column (
                modifier = Modifier
                    .padding(
                        top = 14.dp,
                        start = 8.dp
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "사용자 권한 확인 안내",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            modifier = Modifier
                                .padding(top = 4.dp),
                            text = "현재 아래 권한을 사용하고 있습니다!",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
                
                Spacer(
                    width = setUpDialogWidth(420.dp),
                    height = 10.dp,
                    isBottomBorder = true
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Box {
                            Row {
                                AsyncImage(
                                    modifier = Modifier.size(36.dp),
                                    model = R.drawable.person_permi,
                                    contentDescription = "권한 확인용 신체 활동"
                                )

                                Column(
                                    modifier = Modifier.padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = "신체 활동 권한 (필수)",
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "걷기, 달리기 등의 활동을 추적합니다!",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }

                        Box {
                            Row {
                                AsyncImage(
                                    modifier = Modifier.size(36.dp),
                                    model = R.drawable.heart_permi,
                                    contentDescription = "권한 확인용 활동 센서"
                                )

                                Column(
                                    modifier = Modifier.padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = "생체 신호 센서 권한 (필수)",
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "백그라운드에서 실행되는 동안 정보를 액세스합니다.",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }

                        Box {
                            Row {
                                AsyncImage(
                                    modifier = Modifier.size(36.dp),
                                    model = R.drawable.noticiation_permi,
                                    contentDescription = "권한 확인용 알림"
                                )

                                Column(
                                    modifier = Modifier.padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = "알림 권한 (선택)",
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "앱에서 걸음 수 정보를 알림을 제공 받습니다!",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }

                        Box {
                            Row {
                                AsyncImage(
                                    modifier = Modifier.size(36.dp),
                                    model = R.drawable.location_permi,
                                    contentDescription = "권한 확인용 위치"
                                )

                                Column(
                                    modifier = Modifier.padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = "위치 권한 (선택)",
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "현재 위치와 실시간 위치를 추적합니다!",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(width = 0.dp, height = 26.dp)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CustomButton(
                        type = ButtonType.PermissionStatus.CANCEL,
                        width = 100.dp,
                        height = 40.dp,
                        text = "취소",
                        backgroundColor = Color(0xFFE91E63)
                    )

                    CustomButton(
                        type = ButtonType.PermissionStatus.CLICK,
                        width = 100.dp,
                        height = 40.dp,
                        text = "확인",
                        backgroundColor = Color(0xFF5c9afa),
                        onNavigateToLogin = {
                            isPermissionPopup.value = false
                            onNavigateToLogin()
                        }
                    )
                }
            }
        }
    }
}