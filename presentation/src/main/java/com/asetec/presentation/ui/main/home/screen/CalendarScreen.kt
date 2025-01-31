package com.asetec.presentation.ui.main.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asetec.presentation.component.grid.ActivateGrid
import com.asetec.presentation.viewmodel.ActivityLocationViewModel

@Preview
@Composable
fun CalendarScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 12.dp, start = 12.dp)
    ) {
        Text(
            text = "회원님의 활동 내역",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .width(360.dp)
                .padding(top = 12.dp)
        ) {
            ActivateGrid()
        }
    }
}

@Composable
fun PreviewCalendarGrid(monthDays: Int, activeDays: Set<Int>) {
    val columns = 9
    val rows = (monthDays + columns - 1) / columns

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 12.dp)
    ) {
        repeat(rows) { rowIndex ->
            Row {
                repeat(columns) { columnIndex ->
                    val day = rowIndex * columns + columnIndex + 1

                    if (day <= monthDays) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp)
                                .background(
                                    if (day in activeDays) Color.Blue else Color.Gray,
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
            }
        }
    }
}