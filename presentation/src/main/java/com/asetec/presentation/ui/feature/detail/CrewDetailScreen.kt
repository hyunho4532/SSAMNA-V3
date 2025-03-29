package com.asetec.presentation.ui.feature.detail

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.asetec.domain.model.dto.ActivateNotificationDTO
import com.asetec.domain.model.dto.CrewDTO
import com.asetec.presentation.component.row.ActivateTabRow
import com.asetec.presentation.viewmodel.CrewViewModel
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun CrewDetailScreen(
    crewList: List<CrewDTO>,
    navController: NavController = rememberNavController(),
    context: Context,
    crewViewModel: CrewViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        crewViewModel.notificationAll()
    }

    val pages = listOf("랭킹", "기록")

    val notificationData = crewViewModel.notification.collectAsState()

    val crewId = remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        crewList.forEach { crew ->
            crewId.intValue = crew.crewId

            val imageName = crew.picture.replace("R.drawable.", "")
            val imageResId = context.resources.getIdentifier(
                imageName,
                "drawable",
                context.packageName
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(id = imageResId),
                    contentDescription = "크루 상세 이미지",
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 12.dp, start = 6.dp, end = 6.dp)
            ) {
                Column {
                    Text(
                        text = crew.title,
                        fontSize = 18.sp
                    )

                    notificationData.value.forEach { notification ->
                        val idxArray = notification.crewId["idx"]?.jsonArray

                        val matchingIdx = idxArray!!.firstOrNull {
                            it.jsonObject["id"]?.jsonPrimitive?.int == crewId.intValue
                        }

                        val matchingId = matchingIdx!!.jsonObject["id"]!!.jsonPrimitive.int

                        val sumFeed = notificationData.value
                            .filter { crewId.intValue == matchingId }
                            .sumOf { it.feed }

                        Text(
                            text = sumFeed.toString(),
                            fontSize = 15.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        ActivateTabRow(
            pages = pages,
            dataList = notificationData.value,
            crewId = crewId.intValue,
            type = "crew"
        )
    }
}