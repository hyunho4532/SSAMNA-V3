package com.asetec.presentation.component.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.asetec.presentation.component.util.FormatImpl

@Composable
fun ActivateGrid() {
    val list = (1..FormatImpl.getMonthDays()).map { it.toString() }

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        content = {
            items(list.size) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .background(
                            Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    )
}