package com.asetec.presentation.ui.feature.crew

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.presentation.viewmodel.JsonParseViewModel

@Composable
fun CrewScreen(
    jsonParseViewModel: JsonParseViewModel = hiltViewModel()
) {
    val crewData = jsonParseViewModel.crewJsonData

    LaunchedEffect(key1 = Unit) {
        if (jsonParseViewModel.activateJsonData.isEmpty()) {
            jsonParseViewModel.activateJsonParse("crew.json", "crew")
        }
    }

    Column {
        Text(
            text = "땀나! 에서 등록한 크루",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}