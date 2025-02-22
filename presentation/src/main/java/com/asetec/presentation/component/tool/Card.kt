package com.asetec.presentation.component.tool

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.asetec.domain.model.state.Activate
import com.asetec.domain.model.dto.ActivateDTO
import com.asetec.domain.model.state.Challenge
import com.asetec.domain.model.dto.ChallengeDTO
import com.asetec.domain.model.state.ActivateForm
import com.asetec.domain.model.user.User
import com.asetec.presentation.R
import com.asetec.presentation.component.util.responsive.setUpWidth
import com.asetec.presentation.enum.CardType
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun CustomCard(width: Dp, height: Dp, text: String, id: Int) {
    Card (
        modifier = Modifier
            .width(width)
            .height(height)
            .shadow(
                elevation = 3.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    painter = painterResource(id = id),
                    contentDescription = "로고",
                    tint = Color.Unspecified
                )
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun ReportCard(userState: User) {

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
        }
    )

    Card (
        modifier = Modifier
            .width(setUpWidth())
            .aspectRatio(3f / 4f)
            .shadow(
                elevation = 3.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.padding(top = 24.dp)
            ) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                        .clickable {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                )
            }

            Text(
                text = "${userState.name} : ${userState.age.toInt()}살",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 6.dp)
            )

            Text(
                text = userState.email,
                fontSize = 16.sp,
            )

            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 34.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .height(80.dp)
                ) {
                    Text(
                        text = "최근 운동을 진행한 적이 있으신가요? ${userState.recentExerciseCheck}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Text(
                        text = "최근 진행하고 있는 운동: ${userState.recentExerciseName}",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .height(80.dp)
                ) {
                    Text(
                        text = "하루에 걷기 또는 달리기를 하시나요? ${userState.recentWalkingCheck}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Text(
                        text = "주: ${userState.recentWalkingOfWeek}회 ${userState.recentWalkingOfTime}분씩 진행!",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .height(80.dp)
                ) {
                    Text(
                        text = "운동 중 목표 기간이 있습니까? ${userState.targetPeriod}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun activateCard(
    context: Context? = LocalContext.current,
    height: Dp,
    backgroundColor: Color = Color.White,
    borderStroke: Int? = 0,
    activate: Activate? = Activate(),
    activateDTO: ActivateDTO? = ActivateDTO(),
    showBottomSheet: MutableState<Boolean>? = mutableStateOf(false),
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    cardType: CardType
) {
    val imageName = activate?.assets?.replace("R.drawable.", "")
    val imageResId = context?.resources?.getIdentifier(imageName, "drawable", context.packageName)

    Card (
        modifier = Modifier
            .width(setUpWidth())
            .height(height)
            .padding(top = 8.dp, start = 8.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(
                    color = Color.Gray,
                    bounded = true
                )
            ) {
                if (cardType == CardType.ActivateStatus.Running) {
                    showBottomSheet?.value = false
                    activityLocationViewModel.setActivateName(
                        activateResId = imageResId!!,
                        activateName = activate!!.name
                    )
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(borderStroke!!.dp, Color.Gray)
    ) {
        if (cardType == CardType.ActivateStatus.Running) {
            Row {
                Text(
                    text = activate!!.name,
                    modifier = Modifier
                        .padding(top = 4.dp, start = 4.dp)
                )

                Spacer(width = 4.dp, height = 0.dp)

                Image(
                    modifier = Modifier
                        .size(22.dp)
                        .padding(top = 4.dp),
                    painter = painterResource(id = imageResId!!),
                    contentDescription = "활동 종류 아이콘"
                )
            }

            Text(
                text = activate!!.description,
                modifier = Modifier
                    .padding(top = 4.dp, start = 4.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
        } else {
            Row(
                modifier = Modifier
                    .padding(top = 6.dp, start = 4.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier
                        .size(42.dp),
                    painter = painterResource(id = activateDTO!!.status["status_icon"]?.jsonPrimitive!!.int),
                    contentDescription = "운동 했던 날 상태 아이콘"
                )

                Spacer(width = 8.dp, height = 0.dp)

                Column {
                    Text(
                        text = activateDTO.todayFormat
                    )

                    Spacer(width = 0.dp, height = 4.dp)

                    Text(
                        text = "${activateDTO.status["status_title"]} : ${activateDTO.cul["goal_count"]}걸음!",
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        modifier = Modifier
                            .size(22.dp),
                        painter = painterResource(id = activateDTO.running["running_icon"]?.jsonPrimitive!!.int),
                        contentDescription = "러닝 상태 아이콘"
                    )

                    Text(
                        text = "${activateDTO.running["running_title"]}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "시간")
                    Text(
                        text = activateDTO!!.time
                    )
                }
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "칼로리")
                    Text(
                        text = String.format("%.2f", activateDTO!!.cul["kcal_cul"])
                    )
                }
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "km")
                    Text(
                        text = "${activateDTO!!.cul["km_cul"]}"
                    )
                }
            }
        }
    }
}

@Composable
fun activateFormCard(
    context: Context? = LocalContext.current,
    height: Dp,
    backgroundColor: Color = Color.White,
    borderStroke: Int? = 0,
    activateForm: ActivateForm? = ActivateForm(),
    activateDTO: ActivateDTO? = ActivateDTO(),
    showBottomSheet: MutableState<Boolean>? = mutableStateOf(false),
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    cardType: CardType
) {
    val imageName = activateForm?.assets?.replace("R.drawable.", "")
    val imageResId = context?.resources?.getIdentifier(imageName, "drawable", context.packageName)

    Card (
        modifier = Modifier
            .width(setUpWidth())
            .height(height)
            .padding(top = 8.dp, start = 8.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(
                    color = Color.Gray,
                    bounded = true
                )
            ) {
                if (cardType == CardType.ActivateStatus.Form) {
                    showBottomSheet?.value = false
                    activityLocationViewModel.setActivateFormName(
                        activateFormResId = imageResId!!,
                        activateFormName = activateForm!!.name
                    )
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(borderStroke!!.dp, Color.Gray)
    ) {
        if (cardType == CardType.ActivateStatus.Form) {
            Row {
                Text(
                    text = activateForm!!.name,
                    modifier = Modifier
                        .padding(top = 4.dp, start = 4.dp)
                )

                Spacer(width = 4.dp, height = 0.dp)

                Image(
                    modifier = Modifier
                        .size(22.dp)
                        .padding(top = 4.dp),
                    painter = painterResource(id = imageResId!!),
                    contentDescription = "활동 형태 아이콘"
                )
            }

            Text(
                text = activateForm!!.description,
                modifier = Modifier
                    .padding(top = 4.dp, start = 4.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
fun challengeCard(
    challenge: Challenge,
    height: Dp,
    onChallengeIsPopup: (Int, Boolean) -> Unit
) {
    Card (
        modifier = Modifier
            .width(setUpWidth())
            .height(height)
            .padding(top = 8.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(
                    color = Color.Gray,
                    bounded = true
                )
            ) {
                onChallengeIsPopup(challenge.index, true)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(2.dp, Color.Gray)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp, start = 4.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = challenge.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Image(
                        modifier = Modifier.padding(end = 4.dp),
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = "추가 아이콘"
                    )
                }

                Text(
                    text = challenge.description,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
fun challengeRegistrationCard(
    challengeDTO: ChallengeDTO,
    height: Dp,
    sumKm: Float,
    sumCount: Int
) {

    var currentProcess by remember {
        mutableStateOf(0f)
    }

    LaunchedEffect(key1 = sumKm, key2 = sumCount) {
        currentProcess = if (challengeDTO.type == "running") {
            sumKm.coerceIn(0f, challengeDTO.goal.toFloat())
        } else {
            sumCount.coerceIn(0, challengeDTO.goal.toFloat().toInt()).toFloat()
        }
    }

    Card (
        modifier = Modifier
            .width(setUpWidth())
            .height(height)
            .padding(top = 8.dp, start = 8.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(
                    color = Color.Gray,
                    bounded = true
                )
            ) {
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = challengeDTO.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = challengeDTO.todayDate,
                    fontSize = 12.sp
                )

                if (challengeDTO.type != "비어있음") {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .padding(top = 12.dp),
                        progress = {
                            currentProcess / challengeDTO.goal
                        },
                        trackColor = Color.LightGray,
                        color = Color(0xFF5c9afa),
                        strokeCap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

/**
 * 사용자의 날자에 따른 운동 내역을 보여준다.
 */
@Composable
fun historyActivateCard(
    activateDTO: ActivateDTO,
    height: Dp
) {
    Card (
        modifier = Modifier
            .width(setUpWidth())
            .height(height)
            .padding(top = 24.dp, start = 8.dp)
            .shadow(
                elevation = 6.dp
            )
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(
                    color = Color.Gray,
                    bounded = true
                )
            ) {
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp, start = 4.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = activateDTO.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = activateDTO.todayFormat,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}