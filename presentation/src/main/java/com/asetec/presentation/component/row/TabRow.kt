package com.asetec.presentation.component.row

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomTabRow(pages: List<String>, todayList: List<String>) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val a

    TabRow(
        backgroundColor = Color.White,
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        pages.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            )
        }
    }

    HorizontalPager(
        count = pages.size,
        state = pagerState
    ) { page ->
        when (page) {
            1 -> {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = page.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }
        }
    }
}