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
import com.asetec.presentation.component.util.getLastMonth
import com.asetec.presentation.component.util.getThisMonth

@Composable
fun Month(kcalList: List<KcalEntry>, kmList: List<KmEntry>) {

    val kcalOfThisMonth = getThisMonth(
        type = "kcal",
        kcalList = kcalList
    )

    val kcalOfLastMonth = getLastMonth(
        type = "kcal",
        kcalList = kcalList)

    val kmOfThisMonth = getThisMonth(
        type = "km",
        kmList = kmList
    )

    val kmOfLastMonth = getLastMonth(
        type = "km",
        kmList = kmList)

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
                    text = "이번 달",
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
                    text = "저번 달",
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
                    text = "$kcalOfThisMonth",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "$kcalOfLastMonth",
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
                    text = "$kmOfThisMonth",
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
                    text = "$kmOfLastMonth",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}