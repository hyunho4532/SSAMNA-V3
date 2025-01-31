package com.asetec.presentation.component.grid

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.asetec.presentation.component.util.FormatImpl
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivateGrid(
    todayList: List<String>
) {
    val list = (1..FormatImpl("YY:MM:DD").getMonthDays()).map { it }

    val todayDays = todayList.mapNotNull {
        FormatImpl("YY:MM:DD").parseMonthDays(it)?.dayOfMonth
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        content = {
            items(list.size) { index ->
                val days = list[index]
                val isToday = todayDays.contains(days)

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