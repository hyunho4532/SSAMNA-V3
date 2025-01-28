package com.asetec.presentation.component.aside

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.domain.model.user.User
import com.asetec.presentation.R
import com.asetec.presentation.component.dialog.ActivateBottomSheet
import com.asetec.presentation.component.dialog.TimeBottomSheet
import com.asetec.presentation.component.tool.CustomButton
import com.asetec.presentation.component.tool.Spacer
import com.asetec.presentation.enum.ButtonType
import com.asetec.presentation.viewmodel.SensorManagerViewModel
import com.asetec.presentation.viewmodel.UserViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAside(
    context: Context,
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    userList: State<User>
) {

    val activates by sensorManagerViewModel.activates.collectAsState()

    val showActivateBottomSheet = remember {
        mutableStateOf(false)
    }

    val showTimeBottomSheet = remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(
                        color = Color.Gray,
                        bounded = true
                    )
                ) {
                    showActivateBottomSheet.value = true
                }
        ) {
            Text(
                text = "활동 종류",
                modifier = Modifier.padding(top = 8.dp, start = 14.dp)
            )

            Row (
                modifier = Modifier.padding(top = 32.dp, start = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = activates.activateResId),
                    contentDescription = "달리는 사람 아이콘"
                )

                Text(
                    text = activates.activateName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(
                        color = Color.Gray,
                        bounded = true
                    )
                ) {
                    showTimeBottomSheet.value = true
                }
        ) {
            Text(
                text = "운동 시간",
                modifier = Modifier.padding(top = 8.dp, start = 14.dp)
            )

            Row (
                modifier = Modifier.padding(top = 32.dp, start = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.baseline_access_time_24),
                    contentDescription = "운동 시간 아이콘"
                )

                Spacer(width = 2.dp, height = 0.dp)

                Text(
                    text = "${userList.value.recentWalkingOfTime}분",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(
                        color = Color.Gray,
                        bounded = true
                    )
                ) {
                    showActivateBottomSheet.value = true
                }
        ) {
            Text(
                text = "목표 거리",
                modifier = Modifier.padding(top = 8.dp, start = 14.dp)
            )

            Row (
                modifier = Modifier.padding(top = 32.dp, start = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.baseline_fmd_good_24),
                    contentDescription = "목표 지점 아이콘"
                )

                Spacer(width = 2.dp, height = 0.dp)

                Text(
                    text = "거리",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        CustomButton(
            type = ButtonType.RunningStatus.OPEN,
            width = 110.dp,
            height = 36.dp,
            text = sensorManagerViewModel.getSavedButtonNameState()!!,
            showIcon = false,
            backgroundColor = Color(0xFF5c9afa),
            navController = null,
            context = context,
            shape = "Circle"
        )
    }

    ActivateBottomSheet(
        context = context,
        sheetState = sheetState,
        showBottomSheet = showActivateBottomSheet
    )

    TimeBottomSheet(
        context = context,
        sheetState = sheetState,
        showBottomSheet = showTimeBottomSheet
    )
}