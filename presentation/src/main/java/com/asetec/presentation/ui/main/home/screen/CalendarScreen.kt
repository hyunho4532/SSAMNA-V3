package com.asetec.presentation.ui.main.home.screen

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.domain.model.state.ActivateDTO
import com.asetec.domain.model.user.User
import com.asetec.presentation.R
import com.asetec.presentation.component.grid.ActivateGrid
import com.asetec.presentation.component.row.CustomTabRow
import com.asetec.presentation.component.tool.historyActivateCard
import com.asetec.presentation.component.util.responsive.setUpWidth
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import java.time.LocalDate

@Composable
fun CalendarScreen(
    activateList: State<List<ActivateDTO>>,
    userList: State<User>,
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel()
) {
    val activateData = activityLocationViewModel.activateData.collectAsState()
    val pages = listOf("매주", "매달", "연간")

    var currentMonth by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mutableStateOf(LocalDate.now())
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

    val todayList = activateList.value.map {
        it.todayFormat.substring(0, 11)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 12.dp, start = 12.dp)
    ) {
        Text(
            text = "${userList.value.name}님의 활동 내역",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {
                    currentMonth = currentMonth.minusMonths(1)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_left_24),
                    contentDescription = "저번 월로 이동"
                )
            }

            Text(
                text = "${currentMonth.year}년 ${
                    currentMonth.monthValue.toString().padStart(2, '0')
                }일",
                fontSize = 16.sp
            )

            IconButton(
                onClick = {
                    currentMonth = currentMonth.plusMonths(1)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                    contentDescription = "다음 월로 이동"
                )
            }
        }

        Box(
            modifier = Modifier
                .width(setUpWidth())
                .padding(top = 12.dp)
        ) {
            ActivateGrid(
                yearMonth = currentMonth,
                todayList = todayList
            )
        }

        activateData.value.forEach {
            historyActivateCard(
                activateDTO = it,
                height = 80.dp
            )
        }

        Box(
            modifier = Modifier
                .width(setUpWidth())
                .padding(top = 24.dp)
        ) {
            CustomTabRow(
                pages = pages,
                todayList = todayList
            )
        }
   }
}