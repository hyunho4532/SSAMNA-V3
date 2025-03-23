package com.asetec.presentation.ui.feature.detail

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.asetec.domain.model.dto.CrewDTO
import com.asetec.presentation.viewmodel.CrewViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
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

    val notificationData = crewViewModel.notification.collectAsState()

    val idx = notificationData.value.map {
        it.crewId["idx"]
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        idx.forEach { jsonElement ->
            if (jsonElement is JsonArray) {
                jsonElement.forEach { item ->
                    if (item is JsonObject) {
                        val notificationCrewId = item["id"]?.jsonPrimitive?.contentOrNull!!.toInt()

                        crewList.forEach { crew ->
                            notificationData.value.forEach { notification ->
                                if (crew.crewId == notificationCrewId) {
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
                                            .padding(top = 12.dp, start = 6.dp)
                                    ) {
                                        Text(
                                            text = crew.crewId.toString(),
                                            fontSize = 18.sp
                                        )
                                    }

                                    Text(
                                        text = notification.title
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}