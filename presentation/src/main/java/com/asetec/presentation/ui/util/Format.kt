package com.asetec.presentation.ui.util

import java.time.LocalDate

sealed class Format {
    abstract fun todayFormatDate(): String
    abstract fun calculateDistanceToKm(steps: Int): Double
}