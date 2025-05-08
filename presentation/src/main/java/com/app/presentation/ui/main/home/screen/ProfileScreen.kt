package com.app.presentation.ui.main.home.screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.domain.model.entry.PolygonBoxItem
import com.app.domain.model.user.User
import com.app.presentation.R
import com.app.presentation.component.box.polygon.PolygonBox
import com.app.presentation.component.dialog.ChallengeBottomSheet
import com.app.presentation.component.dialog.ShowChallengeDetailDialog
import com.app.presentation.component.tool.Spacer
import com.app.presentation.component.tool.activateCard
import com.app.presentation.component.tool.challengeRegistrationCard
import com.app.presentation.component.util.calculatorActivateCardWeight
import com.app.presentation.component.util.responsive.setUpWidth
import com.app.domain.model.enum.CardType
import com.app.domain.model.state.ChallengeMaster
import com.app.domain.model.state.CrewMaster
import com.app.presentation.component.admob.Banner
import com.app.presentation.viewmodel.ActivityLocationViewModel
import com.app.presentation.viewmodel.ChallengeViewModel
import com.app.presentation.viewmodel.CrewViewModel
import com.app.presentation.viewmodel.UserViewModel
import com.google.gson.Gson
import kotlinx.serialization.json.double
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

@SuppressLint("DiscouragedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController = rememberNavController(),
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    challengeViewModel: ChallengeViewModel = hiltViewModel(),
    crewViewModel: CrewViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    userList: State<User>,
    context: Context
) {
    val activateData  = activityLocationViewModel.activateData.collectAsState()
    val challengeData = challengeViewModel.challengeData.collectAsState()
    val challengeDetailData = challengeViewModel.challengeDetailData.collectAsState()
    val crewData = crewViewModel.crew.collectAsState()

    val challengeMaster = remember {
        mutableStateListOf<ChallengeMaster>()
    }

    var sumCount by remember {
        mutableIntStateOf(0)
    }

    var sumKcal by remember {
        mutableDoubleStateOf(0.0)
    }

    var sumKm by remember {
        mutableDoubleStateOf(0.0)
    }

    val showChallengeBottomSheet = remember {
        mutableStateOf(false)
    }

    val showChallengeDialogPopup = remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    LaunchedEffect(key1 = Unit) {
        val googleId = userViewModel.getSavedLoginState()
        val challengeMasterAll = challengeViewModel.selectChallengeAll()

        activityLocationViewModel.selectActivityFindByGoogleId(userList.value.id)
        challengeMaster.addAll(challengeMasterAll)
        challengeViewModel.selectChallengeByGoogleId(googleId = googleId)
        crewViewModel.crewFindById(googleId = googleId)
    }

    val challengeDataTitle: List<String> = challengeMaster.map {
        it.name
    }

    LaunchedEffect(key1 = activateData.value) {
        if (activateData.value.isNotEmpty()) {
            sumCount = activateData.value.sumOf {
                it.cul["goal_count"]?.jsonPrimitive?.int ?: 0
            }
            sumKcal = activateData.value.sumOf {
                it.cul["kcal_cul"]?.jsonPrimitive?.double ?: 0.0
            }
            sumKm = activateData.value.sumOf {
                it.cul["km_cul"]?.jsonPrimitive?.double ?: 0.0
            }
        }
    }

    val polygonBoxItems = listOf(
        PolygonBoxItem("걸음 수", sumCount),
        PolygonBoxItem("칼로리", sumKcal),
        PolygonBoxItem("km", sumKm)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp, start = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            modifier = Modifier
                .width(setUpWidth()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(76.dp)
                    .clickable {
                        Log.d("ProfileScreen", "1234")
                    }
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.default_user),
                contentDescription = "프로필 아이콘"
            )

            Spacer(width = 0.dp, height = 8.dp)

            Text(
                text = userList.value.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }

        Row (
            modifier = Modifier
                .width(setUpWidth())
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            polygonBoxItems.forEach { item ->
                PolygonBox(
                    title = item.title,
                    data = item.data
                )
            }
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
                    .size(28.dp)
                    .clickable {
                        navController.navigate("activate")
                    },
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = "활동 아이콘"
            )
        }
        Column (
            modifier = Modifier
                .height(
                    calculatorActivateCardWeight(
                        data = activateData,
                        minHeight = 160,
                        maxHeight = 320
                    )
                )
                .verticalScroll(rememberScrollState())
        ) {
            activateData.value.forEach { activateDTO ->
                activateCard(
                    height = 160.dp,
                    borderStroke = 1,
                    activateDTO = activateDTO,
                    cardType = CardType.ActivateStatus.Activity,
                    navController = navController
                )
            }
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
                    navController.navigate("crew")
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "크루",
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
                .width(setUpWidth())
                .height(116.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Row {
                crewData.value.forEachIndexed { index, crewItem ->
                    val imageName = crewItem.picture.replace("R.drawable.", "")
                    val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

                    Box(
                        modifier = Modifier
                            .padding(
                                top = 6.dp,
                                start = if (index == 0) 0.dp else 18.dp
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
                                val crewList = listOf(crewItem)
                                val crew = Uri.encode(Gson().toJson(crewList))
                                navController.navigate("crewDetail/$crew")
                            },
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = imageResId),
                                contentDescription = "크루 아이콘",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(86.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 4.dp),
                                text = crewItem.title,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
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
                .height(
                    calculatorActivateCardWeight(
                        data = challengeData,
                        minHeight = 80,
                        maxHeight = 160
                    )
                )
                .verticalScroll(rememberScrollState())
        ) {
            challengeData.value.forEach { challengeDTO ->
                challengeRegistrationCard(
                    challengeDTO = challengeDTO,
                    height = 80.dp,
                    sumKm = sumKm.toFloat(),
                    sumCount = sumCount
                ) { isPopup ->
                    challengeViewModel.selectChallengeById(challengeDTO.id) {
                        showChallengeDialogPopup.value = isPopup
                    }
                }
            }
        }

        Spacer(width = 0.dp, height = 24.dp)

        Column(
            modifier = Modifier.width(setUpWidth()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Banner()
        }
    }

    if (showChallengeDialogPopup.value) {
        if (challengeDetailData.value.isNotEmpty()) {
            ShowChallengeDetailDialog(
                isShowChallengePopup = showChallengeDialogPopup,
                challengeDetailData = challengeDetailData.value,
                sumKm = sumKm,
                sumCount = sumCount
            )
        }
    }

    if (showChallengeBottomSheet.value) {
        ChallengeBottomSheet(
            showBottomSheet = showChallengeBottomSheet,
            sheetState = sheetState,
            challengeDataTitle = challengeDataTitle,
            challengeMaster = challengeMaster
        )
    }
}