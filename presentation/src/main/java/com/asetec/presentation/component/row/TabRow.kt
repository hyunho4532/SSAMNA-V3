package com.asetec.presentation.component.row

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.asetec.domain.model.dto.ActivateDTO
import com.asetec.domain.model.entry.StepEntry
import com.asetec.domain.model.entry.KcalEntry
import com.asetec.domain.model.entry.KmEntry
import com.asetec.presentation.component.row.tab.Month
import com.asetec.presentation.component.row.tab.Week
import com.asetec.presentation.component.row.tab.Year
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlinx.serialization.json.double
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomTabRow(
    pages: List<String>,
    activateList: State<List<ActivateDTO>>
) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val kcalList = activateList.value.map {
        KcalEntry(it.todayFormat.substring(0, 13), it.cul["kcal_cul"]?.jsonPrimitive!!.double)
    }

    val kmList = activateList.value.map {
        KmEntry(it.todayFormat.substring(0, 13), it.cul["km_cul"]?.jsonPrimitive!!.double)
    }

    val stepList = activateList.value.map {
        StepEntry(it.todayFormat.substring(0, 13), it.cul["goal_count"]?.jsonPrimitive!!.int)
    }

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
        modifier = Modifier
            .padding(top = 50.dp),
        count = pages.size,
        state = pagerState
    ) { page ->
        when (page) {
            0 -> Week(
                kcalList = kcalList,
                kmList = kmList,
                stepList = stepList
            )
            1 -> Month(
                kcalList = kcalList,
                kmList = kmList,
                stepList = stepList
            )
            2 -> Year(
                kcalList = kcalList,
                kmList = kmList,
                stepList = stepList
            )
        }
    }
}