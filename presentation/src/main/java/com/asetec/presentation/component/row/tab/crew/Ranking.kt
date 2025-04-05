package com.asetec.presentation.component.row.tab.crew

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asetec.domain.model.state.Ranking
import com.asetec.presentation.R
import com.asetec.presentation.component.util.responsive.setUpWidth

@Composable
fun Ranking(
    rankingList: SnapshotStateList<Ranking>
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 12.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "현재 랭킹 (TOP3)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                modifier = Modifier
                    .size(28.dp),
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = "추가 아이콘"
            )
        }

        rankingList.forEach {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Card(
                    modifier = Modifier
                        .size(width = setUpWidth(), height = 36.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 1.5.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 6.dp),
                            text = it.rank.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        VerticalDivider(
                            modifier = Modifier
                                .padding(start = 6.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = 6.dp),
                                text = it.name
                            )

                            Text(
                                modifier = Modifier
                                    .padding(end = 6.dp),
                                text = it.sumKm.toString() + "km"
                            )
                        }
                    }
                }
            }
        }
    }
}