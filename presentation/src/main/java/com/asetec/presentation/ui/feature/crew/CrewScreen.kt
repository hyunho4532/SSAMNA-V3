package com.asetec.presentation.ui.feature.crew

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.presentation.viewmodel.JsonParseViewModel

@Composable
fun CrewScreen(
    jsonParseViewModel: JsonParseViewModel = hiltViewModel()
) {
    val crewSize = jsonParseViewModel.crewJsonData.size

    val crewData = jsonParseViewModel.crewJsonData.map {
        it
    }

    LaunchedEffect(key1 = Unit) {
        if (jsonParseViewModel.activateJsonData.isEmpty()) {
            jsonParseViewModel.activateJsonParse("crew.json", "crew")
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "땀나! 에서 등록한 크루",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        val pagerState = rememberPagerState(pageCount = {
            crewSize
        })

        HorizontalPager(
            modifier = Modifier
                .padding(top = 6.dp),
            state = pagerState,
            pageSpacing = 16.dp
        ) { page ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                crewData.forEach {
                    if (pagerState.currentPage == it.index) {
                        Text(
                            modifier = Modifier
                                .padding(top = 6.dp, start = 4.dp),
                            text = it.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}