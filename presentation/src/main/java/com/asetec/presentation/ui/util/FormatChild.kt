package com.asetec.presentation.ui.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

data object FormatChildren : Format() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun todayFormatDate(): String {
        val currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h:mm")

        return currentDateTime.format(formatter)
    }

    override fun calculateDistanceToKm(steps: Int): Double {
        /**
         * 한 걸음당 0.75미터로 판단하고 계산한다.
         */
        val stepLengthInKm = 0.75 / 1000.0

        return ((stepLengthInKm * steps) * 100.0).roundToInt() / 100.0
    }
}