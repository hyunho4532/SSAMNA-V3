package com.asetec.presentation.component.row

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asetec.domain.model.state.ActivateDTO
import com.asetec.domain.model.state.KcalEntry
import com.asetec.domain.model.state.KmEntry
import com.asetec.presentation.component.util.getLastWeek
import com.asetec.presentation.component.util.getThisWeek
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomTabRow(
    pages: List<String>,
    activateList: State<List<ActivateDTO>>
) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val kcalList = activateList.value.map {
        KcalEntry(it.todayFormat.substring(0, 11), it.kcal_cul)
    }

    val kmList = activateList.value.map {
        KmEntry(it.todayFormat.substring(0, 11), it.km_cul)
    }

    val kcalOfThisWeek = getThisWeek(
        type = "kcal",
        kcalList = kcalList)

    val kcalOfLastWeek = getLastWeek(
        type = "kcal",
        kcalList = kcalList)

    val kmOfThisWeek = getThisWeek(
        type = "km",
        kmList = kmList
    )

    val kmOfLastWeek = getLastWeek(
        type = "km",
        kmList = kmList)

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
            0 -> {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(top = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "활동",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "이번 주",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "지난 주",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "칼로리",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "$kcalOfThisWeek",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "$kcalOfLastWeek",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "거리 (km)",
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "$kmOfThisWeek",
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = "$kmOfLastWeek",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun SumKcalList() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "활동",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "이번 주",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "지난 주",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Row(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "칼로리",
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "0",
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "0",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Row(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "칼로리",
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "0",
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "0",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}