package com.asetec.presentation.component.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.asetec.domain.model.state.ActivateDTO
import com.asetec.domain.model.state.KcalEntry
import java.time.DayOfWeek
import java.time.LocalDate

fun calculatorActivateCardWeight(activateData: State<List<ActivateDTO>>): Dp {

    val size = activateData.value.size

    return if (size > 0) {
        /**
         * 비례 관계
         */
        (320 * (size / 2f)).coerceIn(160f, 320f).dp
    } else {
        0.dp
    }
}

/**
 * 이번 주 kcal 계산 함수
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getThisWeekKcal(kcalList: List<KcalEntry>): Float {
    val today = LocalDate.now()
    val startOfWeek = today.with(DayOfWeek.MONDAY)
    val endOfWeek = today.with(DayOfWeek.SUNDAY)
    
}