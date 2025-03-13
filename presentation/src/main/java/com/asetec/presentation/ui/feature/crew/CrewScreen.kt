package com.asetec.presentation.ui.feature.crew

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.presentation.R
import com.asetec.presentation.viewmodel.JsonParseViewModel
import com.asetec.presentation.viewmodel.UserViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@SuppressLint("DiscouragedApi")
@Composable
fun CrewScreen(
    context: Context,
    userViewModel: UserViewModel = hiltViewModel(),
    jsonParseViewModel: JsonParseViewModel = hiltViewModel()
) {
    val crewSize = jsonParseViewModel.crewJsonData.size

    val userData = userViewModel.user.collectAsState()
    val crewData = jsonParseViewModel.crewJsonData.map {
        it
    }

    LaunchedEffect(key1 = Unit) {
        if (jsonParseViewModel.activateJsonData.isEmpty()) {
            jsonParseViewModel.activateJsonParse("crew.json", "crew")
        }

        val googleId = userViewModel.getSavedLoginState()
        userViewModel.selectUserFindById(googleId)
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
                    .height(460.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                crewData.forEach {
                    val imageName = it.assets.replace("R.drawable.", "")
                    val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

                    if (pagerState.currentPage == it.index) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(260.dp)
                            ) {
                                Image(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    painter = painterResource(id = imageResId),
                                    contentDescription = "모닝",
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(90.dp)
                            ) {
                                Column {
                                    Text(
                                        text = it.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )

                                    Text(
                                        text = it.description,
                                        fontSize = 14.sp,
                                        color = Color.Gray
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