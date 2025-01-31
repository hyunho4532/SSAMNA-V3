package com.asetec.presentation.component.util

import java.time.LocalDate

sealed class Format {
    abstract fun getTodayFormatDate(): String
    abstract fun calculateDistanceToKm(steps: Int): Double
    abstract fun getMonthDays(): Int
    abstract fun getFormatTime(time: Long): String
}