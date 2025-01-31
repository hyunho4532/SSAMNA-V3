package com.asetec.presentation.component.grid

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.asetec.presentation.component.util.FormatImpl
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivateGrid(
    yearMonth: LocalDate,
    todayList: List<String>
) {
    val list = (1..FormatImpl("YY:MM:DD").getMonthDays(yearMonth)).map { it }

    val activityDays = todayList.map {
        FormatImpl("YY:MM:DD").parseMonthDays(it)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        content = {
            items(list.size) { index ->
                val dayLocalDate = yearMonth.withDayOfMonth(list[index]).format(FormatImpl("YY:MM:DD").formatter)
                val isToday = activityDays.contains(dayLocalDate.toString())

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .background(
                            color = if (isToday) Color(0xFF3C69EA) else Color(0xFFE7E7E7),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    )
}