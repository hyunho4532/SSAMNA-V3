package com.asetec.presentation.component.row.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asetec.domain.model.state.KcalEntry
import com.asetec.domain.model.state.KmEntry
import com.asetec.presentation.component.util.getLastYear
import com.asetec.presentation.component.util.getThisYear

@Composable
fun Year(kcalList: List<KcalEntry>, kmList: List<KmEntry>) {

    val kcalOfThisYear = getThisYear(
        type = "kcal",
        kcalList = kcalList
    )

    val kcalOfLastYear = getLastYear(
        type = "kcal",
        kcalList = kcalList)

    val kmOfThisYear = getThisYear(
        type = "km",
        kmList = kmList
    )

    val kmOfLastYear = getLastYear(
        type = "km",
        kmList = kmList)

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
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
                    text = "올해",
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
                    text = "작년",
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
                    text = "$kcalOfThisYear",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "$kcalOfLastYear",
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
                    text = "$kmOfThisYear",
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
                    text = "$kmOfLastYear",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}