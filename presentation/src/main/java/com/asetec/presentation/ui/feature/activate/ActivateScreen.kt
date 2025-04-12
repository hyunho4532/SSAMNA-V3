package com.asetec.presentation.ui.feature.activate

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.asetec.domain.model.dto.ActivateDTO
import com.asetec.presentation.R
import com.asetec.presentation.component.box.polygon.PolygonBox
import com.asetec.presentation.component.marker.MapMarker
import com.asetec.presentation.component.util.responsive.setUpWidth
import com.asetec.presentation.viewmodel.ActivateViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.double
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun ActivateScreen(
    @ApplicationContext context: Context,
    activateViewModel: ActivateViewModel = hiltViewModel(),
    navController: NavController,
) {
    val activateList = remember {
        mutableStateListOf<ActivateDTO>()
    }
    
    LaunchedEffect(key1 = Unit) {
        if (activateList.isEmpty()) {
            val activates = activateViewModel.select()
            activateList.addAll(activates)
        }
    }

    val scrollState = rememberScrollState()
    var mapTouched by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState, enabled = !mapTouched),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        activateList.forEach { item ->
            val coordsArray = item.coord["coords"]?.jsonArray

            val coordsPolyline = coordsArray!!.map {
                LatLng(it.jsonObject["latitude"]?.jsonPrimitive?.doubleOrNull!!, it.jsonObject["longitude"]?.jsonPrimitive?.doubleOrNull!!)
            }

            Card(
                modifier = Modifier
                    .width(setUpWidth())
                    .height(360.dp)
                    .padding(top = 12.dp)
                    .clickable {
                        navController.navigate("activateDetail/${item.id}")
                    },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    mapTouched = true
                                    tryAwaitRelease()
                                    mapTouched = false
                                }
                            )
                        }
                ) {
                    val firstLatLng = LatLng(
                        coordsArray[0].jsonObject["latitude"]?.jsonPrimitive?.doubleOrNull!!,
                        coordsArray[0].jsonObject["longitude"]?.jsonPrimitive?.doubleOrNull!!
                    )

                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(firstLatLng, 20f)
                    }

                    GoogleMap(
                        modifier = Modifier.matchParentSize(),
                        cameraPositionState = cameraPositionState,
                        uiSettings = MapUiSettings(
                            scrollGesturesEnabled = true,
                            zoomGesturesEnabled = true
                        )
                    ) {
                        coordsArray.forEach { item ->
                            val obj = item.jsonObject
                            MapMarker(
                                context = context,
                                position = LatLng(
                                    obj["latitude"]?.jsonPrimitive?.doubleOrNull!!,
                                    obj["longitude"]?.jsonPrimitive?.doubleOrNull!!
                                ),
                                title = "러닝 시작점",
                                iconResourceId = R.drawable.location_marker
                            )
                        }

                        Polyline(
                            points = coordsPolyline,
                            color = Color.Gray,
                            width = 3f
                        )
                    }
                }

                Column {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        PolygonBox(
                            title = "시간",
                            data =  item.time
                        )

                        PolygonBox(
                            title = "km",
                            data =  item.cul["km_cul"]?.jsonPrimitive?.double ?: 0.0
                        )

                        PolygonBox(
                            title = "kcal",
                            data = item.cul["kcal_cul"]?.jsonPrimitive?.double ?: 0.0
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, start = 6.dp, end = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(top = 2.dp, start = 6.dp)
                    ) {
                        Text(
                            text = item.todayFormat
                        )
                    }
                }
            }
        }
    }
}