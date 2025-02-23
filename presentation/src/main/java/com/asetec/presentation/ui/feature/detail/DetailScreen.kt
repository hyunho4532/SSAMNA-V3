package com.asetec.presentation.ui.feature.detail

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asetec.domain.model.location.Coordinate
import com.asetec.presentation.R
import com.asetec.presentation.component.marker.MapMarker
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DetailScreen(
    googleId: String,
    date: String,
    activityLocationViewModel: ActivityLocationViewModel,
    context: Context,
) {
    val activateData = activityLocationViewModel.activateData.collectAsState()
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(key1 = Unit) {
        activityLocationViewModel.selectActivityFindByIdDate(googleId, date)
    }

    val coordsList: List<Coordinate> = activityLocationViewModel.setCoordList(activateData)

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            if (coordsList.isNotEmpty()) {
                cameraPositionState.move(
                    CameraUpdateFactory.newLatLngZoom(LatLng(coordsList.get(0).latitude, coordsList.get(0).longitude), 18f)
                )

                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(24.dp),
                    cameraPositionState = cameraPositionState
                ) {
                    coordsList.forEach { data ->
                        MapMarker(
                            context = context,
                            position = LatLng(data.latitude, data.longitude),
                            title = "활동 내역",
                            iconResourceId = R.drawable.location_marker
                        )
                    }
                }
            }
        }
    }
}