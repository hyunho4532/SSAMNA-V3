package com.asetec.presentation.component.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

data object FormatImpl : Format() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTodayFormatDate(): String {
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

    override fun getMonthDays(): Int {
        val currentYearMonth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            YearMonth.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return currentYearMonth.lengthOfMonth()
    }

    override fun getFormatTime(time: Long): String {
        val minutes = time / 60
        val seconds = time % 60

        return String.format("%02d:%02d", minutes, seconds)
    }
}