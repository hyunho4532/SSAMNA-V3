package com.asetec.presentation.ui.feature.login

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.asetec.domain.model.user.User
import com.asetec.presentation.R
import com.asetec.presentation.component.row.RadioRow
import com.asetec.presentation.component.tool.Spacer
import com.asetec.presentation.component.util.responsive.setFontSize
import com.asetec.presentation.component.util.responsive.setUpButtonWidth
import com.asetec.presentation.component.util.responsive.setUpSliderWidth
import com.asetec.presentation.component.util.getDPI
import com.asetec.presentation.component.util.responsive.setUpWidth
import com.asetec.presentation.viewmodel.UserViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun UserInfoScreen(
    navController: NavController,
    user: User,
    userViewModel: UserViewModel = hiltViewModel(),
    context: Context
) {

    val densityDpi = getDPI(context)

    val yesORNo = listOf("네", "아니요")

    val userState = userViewModel.user.collectAsState()

    val enableExerciseTextField = remember {
        mutableStateOf(true)
    }

    val enableWalkingTextField = remember {
        mutableStateOf(true)
    }

    val (selectedOption, setSelectedOption) = remember {
        mutableStateOf(yesORNo[0])
    }

    val (selectedOption1, setSelectedOption1) = remember {
        mutableStateOf(yesORNo[0])
    }

    val (selectedOption2, setSelectedOption2) = remember {
        mutableStateOf(yesORNo[0])
    }

    LaunchedEffect(user) {
        if (user.email.isNotEmpty()) {
            userViewModel.mergeAuthStateIntoUserState(user = user)
        }
    }

    LaunchedEffect(userState.value) {
        enableExerciseTextField.value = userState.value.recentExerciseCheck == "네"
        enableWalkingTextField.value = userState.value.recentWalkingCheck == "네"
    }

    CompositionLocalProvider(
        LocalDensity provides Density(
            density = LocalDensity.current.density,
            fontScale = 1f
        )
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            val fontSize = setFontSize(densityDpi)

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Text(
                        text = "회원님의 정보가 필요해요!",
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "정보에 맞게 운동 정보를 제공해드립니다!",
                        modifier = Modifier.padding(top = 48.dp, start = 16.dp),
                        fontSize = 16.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    Row {
                        Text(
                            text = "1. 나이를 선택해주세요!",
                            modifier = Modifier.padding(top = 36.dp, start = 16.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = fontSize
                        )

                        Text(
                            text = "회원님의 나이: ${userState.value.age.toInt()}살",
                            modifier = Modifier.padding(top = 36.dp, start = 12.dp),
                            fontSize = fontSize
                        )
                    }

                    Slider(
                        modifier = Modifier
                            .width(setUpWidth())
                            .padding(top = 52.dp, start = 16.dp),
                        value = userState.value.age,
                        onValueChange = {
                            userViewModel.saveAge(it)
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF42B4F5),
                            activeTrackColor = Color(0xFF156ffa),
                            inactiveTrackColor = Color(0xFF5898fa)
                        ),
                        steps = 99,
                        valueRange = 0f..100f
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(top = 24.dp)
                ) {
                    Row {
                        Text(
                            text = "2. 최근 운동을 하신 적이 있으신가요?",
                            modifier = Modifier.padding(start = 16.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = fontSize
                        )
                    }

                    RadioRow(
                        yesORNo = yesORNo,
                        id = 0,
                        selectedOption = selectedOption,
                        onOptionSelected = setSelectedOption
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .padding(top = 24.dp)
                ) {
                    Text(
                        text = "2-1. 최근 운동을 진행하셨다면 어떤 운동을 하셨나요?",
                        modifier = Modifier.padding(start = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = fontSize
                    )

                    Box(
                        modifier = Modifier.padding(top = 36.dp, start = 16.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .width(240.dp)
                                .height(56.dp),
                            value = userState.value.recentExerciseName ?: "",
                            onValueChange = {
                                userViewModel.saveExerciseName(it)
                            },
                            enabled = enableExerciseTextField.value,
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.hint_recent_exercise),
                                    color = Color.Gray
                                )
                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .padding(top = 32.dp)
                ) {
                    Text(
                        text = "3. 하루에 꾸준히 걷기 또는 달리기를 하시나요?",
                        modifier = Modifier.padding(start = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = fontSize
                    )

                    RadioRow(
                        yesORNo = yesORNo,
                        id = 1,
                        selectedOption = selectedOption1,
                        onOptionSelected = setSelectedOption1
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .padding(top = 24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                    ) {
                        Text(
                            text = "3-1. 조깅이나 걷기를 주 몇 번 하시나요? 몇 분 정도 하시나요?",
                            modifier = Modifier.padding(start = 16.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = fontSize
                        )
                    }

                    Box(
                        modifier = Modifier.padding(top = 32.dp, start = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "주: ",
                                fontWeight = FontWeight.Bold
                            )

                            OutlinedTextField(
                                modifier = Modifier
                                    .width(44.dp)
                                    .height(56.dp),
                                value = userState.value.recentWalkingOfWeek,
                                onValueChange = {
                                    userViewModel.saveWalkingOfWeek(it)
                                },
                                enabled = enableWalkingTextField.value,
                                placeholder = {
                                    Text(
                                        text = stringResource(id = R.string.hint_exercise_week),
                                        color = Color.Gray
                                    )
                                }
                            )

                            Spacer(width = 80.dp, height = 0.dp)

                            Text(
                                text = "시간: ",
                                fontWeight = FontWeight.Bold
                            )

                            OutlinedTextField(
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(56.dp),
                                value = userState.value.recentWalkingOfTime,
                                onValueChange = {
                                    userViewModel.saveWalkingOfTime(it)
                                },
                                enabled = enableWalkingTextField.value,
                                placeholder = {
                                    Text(
                                        text = stringResource(id = R.string.hint_exercise_time),
                                        color = Color.Gray
                                    )
                                }
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(top = 46.dp)
                ) {
                    Text(
                        text = "4. 운동 중 목표 기간이 있습니까?",
                        modifier = Modifier.padding(start = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = fontSize
                    )

                    RadioRow(
                        yesORNo = yesORNo,
                        id = 2,
                        selectedOption = selectedOption2,
                        onOptionSelected = setSelectedOption2
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = {
                            val userStateJson = Uri.encode(Json.encodeToString(userState.value))
                            navController.navigate("report?userState=${userStateJson}")
                        },
                        modifier = Modifier
                            .width(setUpWidth()),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5c9afa)
                        )
                    ) {
                        Text(text = "정보 작성 완료!")
                    }
                }
            }
        }
    }
}