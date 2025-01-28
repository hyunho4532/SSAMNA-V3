package com.asetec.presentation.component.dialog

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.presentation.component.tool.activateCard
import com.asetec.presentation.component.tool.challengeCard
import com.asetec.presentation.enum.CardType
import com.asetec.presentation.viewmodel.JsonParseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivateBottomSheet(
    context: Context,
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState,
    jsonParseViewModel: JsonParseViewModel = hiltViewModel()
) {

    var dataIsLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        if (jsonParseViewModel.activateJsonData.isEmpty()) {
            jsonParseViewModel.activateJsonParse("activate.json", "activate")
        }
        dataIsLoading = true
    }

    if (showBottomSheet.value) {
        if (dataIsLoading) {
            ModalBottomSheet(
                modifier = Modifier
                    .fillMaxSize(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet.value = false },
                containerColor = Color.White
            ) {

                Box(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "활동 종류를 선택해주세요!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    jsonParseViewModel.activateJsonData.forEach { activate ->
                        activateCard(
                            context = context,
                            height = 60.dp,
                            activate = activate,
                            showBottomSheet = showBottomSheet,
                            cardType = CardType.ActivateStatus.Running
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeBottomSheet(
    context: Context,
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState
) {

    var checked by remember {
        mutableStateOf(false)
    }

    if (showBottomSheet.value) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet.value = false },
            containerColor = Color.White
        ) {

            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "시간을 입력해주세요!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "내가 입력한 시간 불러오기",
                        fontSize = 16.sp
                    )
                    Checkbox(
                        checked = checked,
                        onCheckedChange = {
                            if (it) {
                                Toast.makeText(context, "입력한 시간으로 불러왔습니다!", Toast.LENGTH_SHORT).show()
                            }
                            checked = it
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeBottomSheet(
    context: Context,
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState,
    jsonParseViewModel: JsonParseViewModel = hiltViewModel(),
    challengeDataTitle: List<String>
) {

    var dataIsLoading by remember {
        mutableStateOf(false)
    }

    val isChallengeIsPopup = remember {
        mutableStateOf(false)
    }

    val challengeIndex  = remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = Unit) {
        if (jsonParseViewModel.challengeJsonData.isEmpty()) {
            jsonParseViewModel.activateJsonParse("challenge.json", "challenge")
        }
        dataIsLoading = true
    }

    if (showBottomSheet.value) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet.value = false },
            containerColor = Color.White
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                jsonParseViewModel.challengeJsonData.forEach { challenge ->
                    if (!challengeDataTitle.contains(challenge.name)) {
                        challengeCard(
                            challenge = challenge,
                            height = 80.dp,
                        ) { index, isPopup ->
                            challengeIndex.intValue = index
                            isChallengeIsPopup.value = isPopup
                        }
                    }
                }
            }
        }
    }

    if (isChallengeIsPopup.value) {
        ShowChallengeDialog(
            index = challengeIndex,
            isChallengeIsPopup = isChallengeIsPopup,
            challenge = jsonParseViewModel.challengeJsonData
        )
    }
}