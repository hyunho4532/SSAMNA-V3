package com.asetec.presentation.component.dialog

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
import androidx.navigation.NavController
import com.asetec.domain.model.dto.ActivateDTO
import com.asetec.presentation.component.tool.activateCard
import com.asetec.presentation.component.tool.activateFormCard
import com.asetec.presentation.component.tool.challengeCard
import com.asetec.domain.model.enum.CardType
import com.asetec.presentation.component.util.responsive.setUpWidth
import com.asetec.presentation.viewmodel.JsonParseViewModel

/**
 * 활동 종류 Bottom Sheet
 */
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

/**
 * 활동 형태 Bottom Sheet
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivateFormBottomSheet(
    context: Context,
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState,
    jsonParseViewModel: JsonParseViewModel = hiltViewModel()
) {

    var dataIsLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        if (jsonParseViewModel.activateFormJsonData.isEmpty()) {
            jsonParseViewModel.activateJsonParse("activate_form.json", "activate_form")
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
                        "활동 형태를 선택해주세요!",
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
                    jsonParseViewModel.activateFormJsonData.forEach { activateForm ->
                        activateFormCard(
                            context = context,
                            height = 60.dp,
                            activateForm = activateForm,
                            showBottomSheet = showBottomSheet,
                            cardType = CardType.ActivateStatus.Form
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeBottomSheet(
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
                            height = 80.dp
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
            isChallengePopup = isChallengeIsPopup,
            challenge = jsonParseViewModel.challengeJsonData
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivateDetailBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState,
    activateData: State<List<ActivateDTO>>,
    navController: NavController
) {
    if (showBottomSheet.value) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet.value = false },
            containerColor = Color.White
        ) {
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                activateData.value.forEach {
                    Card (
                        modifier = Modifier
                            .width(setUpWidth())
                            .height(60.dp)
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
                                navController.navigate("activateDetail/${it.id}")
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Box(
                            modifier = Modifier.padding(top = 6.dp, start = 6.dp)
                        ) {
                            Column {
                                Text(
                                    text = it.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = it.todayFormat,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}