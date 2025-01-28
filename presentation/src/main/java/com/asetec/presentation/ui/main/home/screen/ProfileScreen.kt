package com.asetec.presentation.ui.main.home.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.domain.model.user.User
import com.asetec.presentation.R
import com.asetec.presentation.component.box.polygon.PolygonBox
import com.asetec.presentation.component.dialog.ChallengeBottomSheet
import com.asetec.presentation.component.tool.Spacer
import com.asetec.presentation.component.tool.activateCard
import com.asetec.presentation.component.tool.challengeRegistrationCard
import com.asetec.presentation.enum.CardType
import com.asetec.presentation.enum.ProfileStatusType
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import com.asetec.presentation.viewmodel.ChallengeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    challengeViewModel: ChallengeViewModel = hiltViewModel(),
    userList: State<User>,
    context: Context
) {

    val activateData  = activityLocationViewModel.activateData.collectAsState()
    val challengeData = challengeViewModel.challengeData.collectAsState()

    val challengeDataTitle: List<String> = challengeData.value.map {
        it.title
    }

    var sumKcal by remember {
        mutableDoubleStateOf(0.0)
    }

    val showChallengeBottomSheet = remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    LaunchedEffect(key1 = Unit) {
        activityLocationViewModel.selectActivityFindById()
        challengeViewModel.selectChallengeById()
    }

    LaunchedEffect(key1 = activateData.value) {
        if (activateData.value.isNotEmpty()) {
            sumKcal = activateData.value.sumOf {
                it.kcal_cul
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 12.dp, start = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Image(
            modifier = Modifier
                .size(46.dp),
            painter =  painterResource(id = R.drawable.baseline_person_24),
            contentDescription = "프로필 아이콘"
        )

        Text(
            text = userList.value.name + "님, 환영합니다!",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Row (
            modifier = Modifier
                .width(350.dp)
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PolygonBox(
                title = "활동",
                activateCount = activateData.value.size,
                profileStatusType = ProfileStatusType.Activate
            )
            PolygonBox(
                title = "칼로리",
                sumKcal = sumKcal,
                profileStatusType = ProfileStatusType.Kcal
            )
            PolygonBox(
                title = "업적",
                profileStatusType = ProfileStatusType.Goal
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, end = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "활동 (${activateData.value.size})",
                fontSize = 22.sp,
            )

            Image(
                modifier = Modifier
                    .size(28.dp),
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = "활동 아이콘"
            )
        }
        Column (
            modifier = Modifier
                .height(320.dp)
                .verticalScroll(rememberScrollState())
        ) {
            activateData.value.forEach { activateDTO ->
                activateCard(
                    height = 160.dp,
                    borderStroke = 2,
                    activateDTO = activateDTO,
                    cardType = CardType.ActivateStatus.Activity
                )
            }
        }

        Spacer(width = 0.dp, height = 46.dp)

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "목표 (1)",
                fontSize = 22.sp,
            )

            Image(
                modifier = Modifier
                    .size(28.dp),
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = "추가 아이콘"
            )
        }

        Spacer(width = 0.dp, height = 46.dp)

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 18.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(
                        color = Color.Gray,
                        bounded = true
                    )
                ) {
                    showChallengeBottomSheet.value = true
                },
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "챌린지 (${challengeData.value.size})",
                fontSize = 22.sp,
            )

            Image(
                modifier = Modifier
                    .size(28.dp),
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = "추가 아이콘"
            )
        }

        Column (
            modifier = Modifier
                .height(320.dp)
                .verticalScroll(rememberScrollState())
        ) {
            challengeData.value.forEach { challengeDTO ->
                challengeRegistrationCard(
                    challengeDTO = challengeDTO,
                    height = 80.dp
                )
            }
        }
    }

    if (showChallengeBottomSheet.value) {
        ChallengeBottomSheet(
            context = context,
            showBottomSheet = showChallengeBottomSheet,
            sheetState = sheetState,
            challengeDataTitle = challengeDataTitle
        )
    }
}